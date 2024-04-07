package com.yeferic.holaflymarvel.presentation.detail.ui.screens

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yeferic.holaflymarvel.R
import com.yeferic.holaflymarvel.core.ui.theme.DarkHolo
import com.yeferic.holaflymarvel.core.ui.widgets.LottieAnimationWidget

data class DetailScreenErrorStateParams(
    val error: String,
    val retryFunction: () -> Unit,
)

@Composable
fun DetailScreenErrorState(params: DetailScreenErrorStateParams) {
    with(params) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LottieAnimationWidget(
                modifier = Modifier.size(72.dp),
                id = R.raw.error,
            )
            Text(
                text = error,
                fontWeight = FontWeight.Light,
                color = DarkHolo,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { retryFunction.invoke() }) {
                Text(
                    text = stringResource(id = R.string.detail_screen_retry_button),
                )
            }
        }
    }
}
