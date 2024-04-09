package com.yeferic.holaflymarvel.presentation.home.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yeferic.holaflymarvel.R
import com.yeferic.holaflymarvel.core.ui.widgets.LottieAnimationWidget

data class HomeScreenParams(
    val navigateToMenuScreen: () -> Unit,
)

enum class HomeScreenTags {
    LOTTIE_IMAGE,
    BUTTON,
    TEXT_BUTTON,
}

@Composable
fun HomeScreen(params: HomeScreenParams) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box {
                LottieAnimationWidget(
                    modifier = Modifier.size(128.dp).testTag(HomeScreenTags.LOTTIE_IMAGE.name),
                    id = R.raw.home_icon,
                )
            }
            Button(modifier = Modifier.testTag(HomeScreenTags.BUTTON.name), onClick = {
                params.navigateToMenuScreen.invoke()
            }) {
                Text(
                    modifier = Modifier.testTag(HomeScreenTags.TEXT_BUTTON.name),
                    text = stringResource(id = R.string.home_screen_button_text),
                )
            }
        }
    }
}
