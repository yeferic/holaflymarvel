package com.yeferic.holaflymarvel.presentation.detail.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import com.yeferic.holaflymarvel.R
import com.yeferic.holaflymarvel.core.ui.widgets.LottieAnimationWidget

enum class DetailScreenLoadingStateTags {
    LOTTIE,
}

@Composable
fun DetailScreenLoadingState() {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .testTag(DetailScreenLoadingStateTags.LOTTIE.name),
    ) {
        LottieAnimationWidget(modifier = Modifier.align(Alignment.Center), id = R.raw.loading_menu)
    }
}
