package com.yeferic.holaflymarvel.presentation.comiclist.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.yeferic.holaflymarvel.R
import com.yeferic.holaflymarvel.core.ui.widgets.AnimatedVisibilityWidget
import com.yeferic.holaflymarvel.core.ui.widgets.LottieAnimationWidget
import com.yeferic.holaflymarvel.core.ui.widgets.OnBottomScroll
import com.yeferic.holaflymarvel.domain.models.Comic

data class ComicsListScreenSuccessStateParams(
    val comics: List<Comic>,
    val isLoadingMore: Boolean,
    val loadingMoreFunction: () -> Unit,
    val goToComicDetailScreen: (Long) -> Unit,
)

@Composable
fun ComicsListScreenSuccessState(params: ComicsListScreenSuccessStateParams) {
    val listState = rememberLazyStaggeredGridState()

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 4.dp),
    ) {
        LazyVerticalStaggeredGrid(
            state = listState,
            columns = StaggeredGridCells.Adaptive(128.dp),
            verticalItemSpacing = 4.dp,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            content = {
                items(params.comics) { comic ->
                    AsyncImage(
                        model = comic.imageUrl,
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier =
                            Modifier
                                .clickable(
                                    enabled = true,
                                ) {
                                    params.goToComicDetailScreen(comic.id)
                                },
                    )
                }
            },
            modifier = Modifier.fillMaxWidth().weight(1f),
        )

        if (params.isLoadingMore) {
            AnimatedVisibilityWidget {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .height(16.dp),
                ) {
                    LottieAnimationWidget(
                        modifier = Modifier.align(Alignment.Center),
                        id = R.raw.loading_scroll,
                    )
                }
            }
        }
    }

    listState.OnBottomScroll {
        params.loadingMoreFunction()
    }
}
