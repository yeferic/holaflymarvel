package com.yeferic.holaflymarvel.presentation.comiclist.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.yeferic.holaflymarvel.presentation.comiclist.states.ComicsListUiState
import com.yeferic.holaflymarvel.presentation.comiclist.ui.screens.ComicsListScreenErrorState
import com.yeferic.holaflymarvel.presentation.comiclist.ui.screens.ComicsListScreenErrorStateParams
import com.yeferic.holaflymarvel.presentation.comiclist.ui.screens.ComicsListScreenLoadingState
import com.yeferic.holaflymarvel.presentation.comiclist.ui.screens.ComicsListScreenSuccessState
import com.yeferic.holaflymarvel.presentation.comiclist.ui.screens.ComicsListScreenSuccessStateParams
import com.yeferic.holaflymarvel.presentation.comiclist.viewmodel.ComicsViewModel

@Composable
fun ComicsListScreen(
    viewModel: ComicsViewModel = hiltViewModel(),
    idCharacter: Long,
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val status by produceState<ComicsListUiState>(
        initialValue = ComicsListUiState.Loading,
        key1 = lifecycle,
        key2 = viewModel,
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.uiState.collect { value = it }
        }
    }

    when (status) {
        is ComicsListUiState.Error -> {
            val errorParams =
                ComicsListScreenErrorStateParams(
                    (status as ComicsListUiState.Error).error.message,
                ) {
                    viewModel.getComics()
                }

            ComicsListScreenErrorState(params = errorParams)
        }

        ComicsListUiState.Loading -> ComicsListScreenLoadingState()
        is ComicsListUiState.Success -> {
            val successParams =
                ComicsListScreenSuccessStateParams(
                    comics = (status as ComicsListUiState.Success).comics,
                    isLoadingMore = (status as ComicsListUiState.Success).loadingMore,
                    loadingMoreFunction = { viewModel.getComics(true) },
                    goToComicDetailScreen = { println(it) },
                )
            ComicsListScreenSuccessState(params = successParams)
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.setIdCharacter(idCharacter)
        viewModel.getComics()
    }
}
