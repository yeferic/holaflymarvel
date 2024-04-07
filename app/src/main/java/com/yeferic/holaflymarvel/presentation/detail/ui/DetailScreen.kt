package com.yeferic.holaflymarvel.presentation.detail.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.yeferic.holaflymarvel.presentation.detail.states.ComicDetailUiState
import com.yeferic.holaflymarvel.presentation.detail.ui.screens.DetailScreenDataLoadedState
import com.yeferic.holaflymarvel.presentation.detail.ui.screens.DetailScreenErrorState
import com.yeferic.holaflymarvel.presentation.detail.ui.screens.DetailScreenErrorStateParams
import com.yeferic.holaflymarvel.presentation.detail.ui.screens.DetailScreenLoadingState
import com.yeferic.holaflymarvel.presentation.detail.viewmodel.DetailViewModel

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    idComic: Long,
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val status by produceState<ComicDetailUiState>(
        initialValue = ComicDetailUiState.Loading,
        key1 = lifecycle,
        key2 = viewModel,
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.uiState.collect { value = it }
        }
    }

    when (status) {
        is ComicDetailUiState.Error -> {
            val errorParams =
                DetailScreenErrorStateParams(
                    (status as ComicDetailUiState.Error).error.message,
                ) {
                    viewModel.getComicInfo()
                }

            DetailScreenErrorState(params = errorParams)
        }

        ComicDetailUiState.Loading -> DetailScreenLoadingState()
        is ComicDetailUiState.DataLoaded -> {
            DetailScreenDataLoadedState((status as ComicDetailUiState.DataLoaded).detail)
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.setIdCharacter(idComic)
        viewModel.getComicInfo()
    }
}
