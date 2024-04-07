package com.yeferic.holaflymarvel.core.commons

sealed class Routes(var route: String) {
    data object HomeScreen : Routes("HomeScreen")

    data object MenuScreen : Routes("MenuScreen")

    data object ComicsScreen : Routes("ComicsScreen/{$COMIC_SCREEN_PARAMETER}") {
        fun getRouteParameter(idCharacter: Long): String = "ComicsScreen/$idCharacter"
    }

    data object DetailScreen : Routes("DetailScreen/{$DETAIL_SCREEN_PARAMETER}") {
        fun getRouteParameter(idComic: Long): String = "DetailScreen/$idComic"
    }

    companion object {
        const val COMIC_SCREEN_PARAMETER = "idCharacter"
        const val DETAIL_SCREEN_PARAMETER = "idComic"
    }
}
