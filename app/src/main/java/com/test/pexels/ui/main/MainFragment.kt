package com.test.pexels.ui.main

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.test.pexels.R
import com.test.pexels.data.model.photo.Photo
import com.test.pexels.ui.Screens
import com.test.pexels.ui.base.frag.BaseFragment
import com.test.pexels.ui.main.viewModels.BookmarkPageViewModel
import com.test.pexels.ui.main.viewModels.MainPageViewModel
import com.test.pexels.ui.main.viewModels.PhotoViewModel
import com.test.pexels.utils.ui.Colors
import com.test.pexels.utils.ui.shimmerBrush
import com.test.pexels.utils.ui.topTabIndicatorOffset
import kotlinx.coroutines.launch

class MainFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(inflater.context).apply { setContent { Root() } }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable private fun Root() {
        val pagerState = rememberPagerState { 2 }
        val scope = rememberCoroutineScope()
        Column(
            modifier = Modifier.background(Colors.background())
        ) {
            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false,
                modifier = Modifier.weight(1f),
            ) {
                when(pagerState.currentPage) {
                    1 -> BookmarkPage { scope.launch { pagerState.animateScrollToPage(0) } }
                    else -> MainPage()
                }
            }
            NavigationBar(pagerState)
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable private fun MainPage(viewModel : MainPageViewModel = viewModel()) {
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
                                modifier = Modifier.animateItemPlacement()
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable private fun MainPhotoItem(
        photo : Photo,
        modifier: Modifier,
        viewModel : PhotoViewModel = viewModel()
    ) {
        var height by rememberSaveable(photo.src.large) { mutableIntStateOf((150..200).random()) }

        LaunchedEffect(photo.src.large) {
            if(viewModel.data[photo.src.large] == null) {
                Glide.with(requireContext())
                    .asBitmap()
                    .load(photo.src.large)
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) { viewModel.data[photo.src.large] = resource }
                        override fun onLoadCleared(placeholder: Drawable?) {}
                    })
            }
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .then(modifier)
                .animateContentSize()
                .clickable { getRouter().navigateTo(Screens.detail(photo)) }
        ) {
            Crossfade(
                targetState = viewModel.data[photo.src.large], label = ""
            ) {
                if(it != null) {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .fillMaxWidth()
                            .onSizeChanged { height = it.height }
                    )
                } else {
                    Spacer(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .fillMaxWidth()
                            .height(height.dp)
                            .background(shimmerBrush())
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable private fun BookmarkPage(
        viewModel : BookmarkPageViewModel = viewModel(),
        goBack : () -> Unit
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.bookmarks),
                fontFamily = FontFamily(Font(R.font.mulish_bold)),
                fontSize = 18.sp,
                color = Colors.onContainer(),
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(top = 24.dp, bottom = 38.dp)
            )

            val bookmarks by viewModel.bookmarks.collectAsState(listOf())

            Crossfade(
                targetState = viewModel.isEmpty,
                label = ""
            ) {
                if(it) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = stringResource(R.string.no_bookmarks),
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
                                    .clickable(onClick = goBack)
                                    .padding(6.dp)
                            )
                        }
                    }
                } else {
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Adaptive(150.dp),
                        verticalItemSpacing = 16.dp,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(horizontal = 24.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(bookmarks) { photo ->
                            Box(
                                modifier = Modifier.clip(RoundedCornerShape(20.dp)),
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                MainPhotoItem(
                                    photo = photo,
                                    modifier = Modifier.animateItemPlacement()
                                )
                                Text(
                                    text = photo.photographer,
                                    fontFamily = FontFamily(Font(R.font.mulish_regular)),
                                    color = Colors.onContainerInvent(),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color(0x66000000))
                                        .padding(vertical = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable private fun NavigationBar(pagerState : PagerState) {
        val scope = rememberCoroutineScope()
        Box(
            modifier = Modifier.shadow(2.dp)
        ) {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = Colors.background(),
                contentColor = Colors.primary(),
                divider = {},
                indicator = @Composable { tabPositions ->
                    if (pagerState.currentPage < tabPositions.size) {
                        TabRowDefaults.PrimaryIndicator(
                            modifier = Modifier.topTabIndicatorOffset(tabPositions[pagerState.currentPage]),
                            color = Colors.primary(),
                            height = 2.dp
                        )
                    }
                },
                modifier = Modifier
                    .background(Colors.background())
                    .navigationBarsPadding()
            ) {
                Tab(
                    selected = pagerState.currentPage == 0,
                    onClick = { scope.launch { pagerState.animateScrollToPage(0) } }
                ) {
                    Image(
                        painter = if(pagerState.currentPage == 0) painterResource(R.drawable.ic_home_selected)
                        else painterResource(R.drawable.ic_home_unselected),
                        contentDescription = null,
                        modifier = Modifier.padding(vertical = 20.dp)
                    )
                }

                Tab(
                    selected = pagerState.currentPage == 1,
                    onClick = { scope.launch { pagerState.animateScrollToPage(1) } }
                ) {
                    Image(
                        painter = if(pagerState.currentPage == 1) painterResource(R.drawable.ic_bookmark_selected)
                        else painterResource(R.drawable.ic_bookmark_unselected),
                        contentDescription = null,
                        modifier = Modifier.padding(vertical = 20.dp)
                    )
                }
            }
        }
    }
}