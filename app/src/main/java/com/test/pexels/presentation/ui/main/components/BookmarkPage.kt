package com.test.pexels.presentation.ui.main.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.test.pexels.R
import com.test.pexels.domain.models.photo.Photo
import com.test.pexels.presentation.core.utils.Colors
import com.test.pexels.presentation.ui.main.viewModels.BookmarkPageViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookmarkPage(
    viewModel : BookmarkPageViewModel = viewModel(),
    goBack : () -> Unit,
    navigateToDetails: (Photo) -> Unit
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
                                modifier = Modifier.animateItemPlacement(),
                                navigateToDetails = navigateToDetails
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