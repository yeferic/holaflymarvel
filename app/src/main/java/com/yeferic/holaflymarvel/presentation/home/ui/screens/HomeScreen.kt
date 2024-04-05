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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yeferic.holaflymarvel.R
import com.yeferic.holaflymarvel.core.ui.widgets.LottieAnimationWidget

data class HomeScreenParams(
    val navigateToMenuScreen: () -> Unit,
)

@Composable
fun HomeScreen(params: HomeScreenParams) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LottieAnimationWidget(modifier = Modifier.size(128.dp), id = R.raw.home_icon)
            Button(onClick = { params.navigateToMenuScreen.invoke() }) {
                Text(
                    text = stringResource(id = R.string.home_screen_button_text),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(HomeScreenParams { })
}
