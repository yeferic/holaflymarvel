package com.yeferic.holaflymarvel.presentation.menu.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.yeferic.holaflymarvel.R
import com.yeferic.holaflymarvel.core.ui.widgets.LottieAnimationWidget

@Composable
fun MenuScreenLoadingState() {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        LottieAnimationWidget(modifier = Modifier.align(Alignment.Center), id = R.raw.loading_menu)
    }
}
