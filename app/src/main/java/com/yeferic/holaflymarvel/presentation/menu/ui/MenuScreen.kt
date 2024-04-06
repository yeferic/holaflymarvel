package com.yeferic.holaflymarvel.presentation.menu.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.yeferic.holaflymarvel.presentation.menu.states.MenuUiStates
import com.yeferic.holaflymarvel.presentation.menu.ui.screens.MenuScreenDataLoadedParams
import com.yeferic.holaflymarvel.presentation.menu.ui.screens.MenuScreenDataLoadedState
import com.yeferic.holaflymarvel.presentation.menu.ui.screens.MenuScreenErrorState
import com.yeferic.holaflymarvel.presentation.menu.ui.screens.MenuScreenErrorStateParams
import com.yeferic.holaflymarvel.presentation.menu.ui.screens.MenuScreenLoadingState
import com.yeferic.holaflymarvel.presentation.menu.viewmodel.MenuViewModel

data class MenuScreenParams(
    val viewModel: MenuViewModel,
)

@Composable
fun MenuScreen(params: MenuScreenParams) {
    with(params.viewModel) {
        val lifecycle = LocalLifecycleOwner.current.lifecycle

        val status by produceState<MenuUiStates>(
            initialValue = MenuUiStates.Loading,
            key1 = lifecycle,
            key2 = uiState,
        ) {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                uiState.collect { value = it }
            }
        }

        when (status) {
            is MenuUiStates.DataLoaded -> {
                val dataParams =
                    MenuScreenDataLoadedParams(
                        characters = (status as MenuUiStates.DataLoaded).characters,
                    ) { println(it) }
                MenuScreenDataLoadedState(dataParams)
            }
            is MenuUiStates.Error -> {
                val paramsError =
                    MenuScreenErrorStateParams(
                        error = (status as MenuUiStates.Error).error.message,
                        ::getCharacterInfo,
                    )

                MenuScreenErrorState(paramsError)
            }

            MenuUiStates.Loading -> MenuScreenLoadingState()
        }
    }
}
