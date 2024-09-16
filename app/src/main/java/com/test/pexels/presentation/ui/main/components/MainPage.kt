package com.test.pexels.presentation.ui.main.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.test.pexels.R
import com.test.pexels.domain.models.photo.Photo
import com.test.pexels.presentation.core.utils.Colors
import com.test.pexels.presentation.ui.main.viewModels.MainPageViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainPage(
    viewModel : MainPageViewModel = viewModel(),
    navigateToDetails: (Photo) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        // Search bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp)
                .background(color = Colors.container(), shape = RoundedCornerShape(50.dp))
                .padding(horizontal = 20.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = null
            )
            var hasFocus by remember { mutableStateOf(false) }
            BasicTextField(
                value = viewModel.searchQuery.ifEmpty { if(!hasFocus) stringResource(R.string.search) else "" },
                onValueChange = { viewModel.setNewSearchQuery(it) },
                cursorBrush = SolidColor(Colors.primary()),
                singleLine = true,
                textStyle = TextStyle(
                    color = if(viewModel.searchQuery.isNotEmpty()) Colors.onContainer() else Colors.secondary(),
                    fontFamily = FontFamily(Font(R.font.mulish_regular)),
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = { viewModel.setNewSearchQuery(viewModel.searchQuery, true) }
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
                    .onFocusChanged { hasFocus = it.hasFocus }
            )
            AnimatedVisibility(
                visible = viewModel.searchQuery.isNotEmpty(),
                enter = slideIn { IntOffset(y = (it.height / 4), x = -100) } + fadeIn(),
                exit = slideOut { IntOffset(y = (it.height / 4), x = -100) } + fadeOut(),
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = null,
                    Modifier.clickable { viewModel.setNewSearchQuery("", immediate = true) }
                )
            }
        }

        // Featured collections
        AnimatedVisibility(
            visible = viewModel.featuredCollection.isNotEmpty()
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 24.dp),
                modifier = Modifier.padding(top = 12.dp)
            ) {
                items(viewModel.featuredCollection) { collection ->
                    val fontWeight by animateIntAsState(
                        if (collection == viewModel.selectedCollection) 700 else 400,
                        label = ""
                    )
                    val backgroundColor by animateColorAsState(
                        if (collection == viewModel.selectedCollection) Colors.primary() else Colors.container(),
                        label = ""
                    )
                    val textColor by animateColorAsState(
                        if(collection == viewModel.selectedCollection) Colors.onPrimary() else Colors.onContainer(),
                        label = ""
                    )
                    Text(
                        text = collection.title,
                        modifier = Modifier
                            .background(
                                color = backgroundColor,
                                shape = RoundedCornerShape(100.dp)
                            )
                            .clip(RoundedCornerShape(100.dp))
                            .clickable {
                                viewModel.selectCollection(collection)
                            }
                            .padding(vertical = 10.dp, horizontal = 20.dp),
                        style = TextStyle(
                            color = textColor,
                            fontFamily = FontFamily(Font(R.font.mulish_regular)),
                            fontWeight = FontWeight(fontWeight)
                        )
                    )
                }
            }
        }

        Column(
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            AnimatedVisibility(visible = viewModel.isLoading) {
                LinearProgressIndicator(
                    trackColor = Colors.background(),
                    color = Colors.primary(),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Main pagination
        Crossfade(
            targetState = viewModel.run {
                isError || (searchQuery.isNotEmpty() && defaultPagination.size <= 0 && !isLoading)
            },
            label = ""
        ) {
            if(it) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    when {
                        viewModel.isError -> Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_no_network),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(Colors.onContainer())
                            )
                            Text(
                                text = stringResource(R.string.try_again),
                                fontFamily = FontFamily(Font(R.font.mulish_bold)),
                                fontSize = 18.sp,
                                color = Colors.primary(),
                                modifier = Modifier
                                    .padding(12.dp)
                                    .clickable { viewModel.tryAgain() }
                                    .padding(12.dp)
                            )
                        }
                        else -> Column (horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = stringResource(R.string.no_results),
                                fontFamily = FontFamily(Font(R.font.mulish_regular)),
                                color = Colors.onContainer()
                            )
                            Text(
                                text = stringResource(R.string.explore),
                                fontFamily = FontFamily(Font(R.font.mulish_bold)),
                                fontSize = 18.sp,
                                color = Colors.primary(),
                                modifier = Modifier
                                    .padding(6.dp)
                                    .clickable { viewModel.setNewSearchQuery("", true) }
                                    .padding(6.dp)
                            )
                        }
                    }
                }
            } else {
                LazyVerticalStaggeredGrid(
                    state = rememberLazyStaggeredGridState().also {
                        if(viewModel.defaultPagination.size == 0) rememberCoroutineScope().launch {
                            it.scrollToItem(0)
                        }
                    },
                    columns = StaggeredGridCells.Adaptive(150.dp),
                    verticalItemSpacing = 16.dp,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 24.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(viewModel.defaultPagination.size) {
                        MainPhotoItem(
                            photo = viewModel.defaultPagination[it],
                            modifier = Modifier.animateItemPlacement(),
                            navigateToDetails = navigateToDetails
                        )
                    }
                }
            }
        }
    }
}