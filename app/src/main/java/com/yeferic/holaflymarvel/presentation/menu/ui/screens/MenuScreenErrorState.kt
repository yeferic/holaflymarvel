package com.yeferic.holaflymarvel.presentation.menu.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yeferic.holaflymarvel.R
import com.yeferic.holaflymarvel.core.ui.theme.DarkHolo
import com.yeferic.holaflymarvel.core.ui.widgets.LottieAnimationWidget

data class MenuScreenErrorStateParams(
    val error: String,
    val retryFunction: () -> Unit,
)

enum class MenuScreenErrorStateTags {
    LOTTIE_IMAGE,
    TEXT_ERROR,
    BUTTON_RETRY,
}

@Composable
fun MenuScreenErrorState(params: MenuScreenErrorStateParams) {
    with(params) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(modifier = Modifier.testTag(MenuScreenErrorStateTags.LOTTIE_IMAGE.name)) {
                LottieAnimationWidget(
                    modifier = Modifier.size(72.dp),
                    id = R.raw.error,
                )
            }
            Text(
                modifier = Modifier.testTag(MenuScreenErrorStateTags.TEXT_ERROR.name),
                text = error,
                fontWeight = FontWeight.Light,
                color = DarkHolo,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                modifier =
                    Modifier.testTag(
                        MenuScreenErrorStateTags.BUTTON_RETRY.name,
                    ),
                onClick = {
                    retryFunction.invoke()
                },
            ) {
                Text(
                    text = stringResource(id = R.string.menu_screen_retry_button),
                )
            }
        }
    }
}
