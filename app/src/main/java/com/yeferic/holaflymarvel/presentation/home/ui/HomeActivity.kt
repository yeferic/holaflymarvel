package com.yeferic.holaflymarvel.presentation.home.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yeferic.holaflymarvel.core.commons.Routes.HOME_SCREEN
import com.yeferic.holaflymarvel.core.commons.Routes.MENU_SCREEN
import com.yeferic.holaflymarvel.core.ui.theme.HolaflyMarvelTheme
import com.yeferic.holaflymarvel.presentation.home.ui.screens.HomeScreen
import com.yeferic.holaflymarvel.presentation.home.ui.screens.HomeScreenParams

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HolaflyMarvelTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val navigationController = rememberNavController()
                    NavHost(
                        navController = navigationController,
                        startDestination = HOME_SCREEN,
                    ) {
                        composable(HOME_SCREEN) {
                            HomeScreen(
                                HomeScreenParams { navigationController.navigate(MENU_SCREEN) },
                            )
                        }
                    }
                }
            }
        }
    }
}
