package com.test.pexels.presentation.core.navigation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.test.pexels.R
import com.test.pexels.presentation.core.utils.Colors
import com.test.pexels.presentation.core.utils.topTabIndicatorOffset
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavigationBar(pagerState : PagerState) {
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