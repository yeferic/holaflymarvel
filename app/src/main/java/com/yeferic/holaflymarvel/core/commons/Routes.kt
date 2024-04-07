package com.yeferic.holaflymarvel.core.commons

sealed class Routes(var route: String) {
    data object HomeScreen : Routes("HomeScreen")

    data object MenuScreen : Routes("MenuScreen")

    data object ComicsScreen : Routes("ComicsScreen/{$COMIC_SCREEN_PARAMETER}") {
        fun getRouteParameter(idCharacter: Long): String = "ComicsScreen/$idCharacter"
    }

    data object DetailScreen : Routes("DetailScreen")

    companion object {
        const val COMIC_SCREEN_PARAMETER = "idCharacter"
    }
}
