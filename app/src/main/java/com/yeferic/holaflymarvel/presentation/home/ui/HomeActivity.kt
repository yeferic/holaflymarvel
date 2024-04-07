package com.yeferic.holaflymarvel.presentation.home.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yeferic.holaflymarvel.core.commons.Routes
import com.yeferic.holaflymarvel.core.commons.Routes.Companion.COMIC_SCREEN_PARAMETER
import com.yeferic.holaflymarvel.core.ui.theme.HolaflyMarvelTheme
import com.yeferic.holaflymarvel.presentation.comiclist.ui.ComicsListScreen
import com.yeferic.holaflymarvel.presentation.home.ui.screens.HomeScreen
import com.yeferic.holaflymarvel.presentation.home.ui.screens.HomeScreenParams
import com.yeferic.holaflymarvel.presentation.menu.ui.MenuScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HolaflyMarvelTheme {
                Surface(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .background(Color.White),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val navigationController = rememberNavController()
                    NavHost(
                        navController = navigationController,
                        startDestination = Routes.HomeScreen.route,
                    ) {
                        composable(Routes.HomeScreen.route) {
                            HomeScreen(
                                HomeScreenParams {
                                    navigationController.navigate(
                                        Routes.MenuScreen.route,
                                    )
                                },
                            )
                        }

                        composable(Routes.MenuScreen.route) {
                            MenuScreen {
                                navigationController.navigate(
                                    Routes.ComicsScreen.getRouteParameter(it),
                                )
                            }
                        }

                        composable(
                            Routes.ComicsScreen.route,
                            arguments =
                                listOf(
                                    navArgument(
                                        COMIC_SCREEN_PARAMETER,
                                    ) {
                                        type = NavType.LongType
                                    },
                                ),
                        ) {
                            val idCharacter =
                                it.arguments?.getLong(COMIC_SCREEN_PARAMETER) ?: 0
                            ComicsListScreen(idCharacter = idCharacter)
                        }
                    }
                }
            }
        }
    }
}
