package com.yeferic.holaflymarvel.presentation.detail.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class DetailScreenLoadingStateKtTest {
    @get:Rule
    val rule = createComposeRule()

    private fun setContent() {
        rule.setContent {
            DetailScreenLoadingState()
        }
    }

    @Test
    fun validateLottieAnimation() {
        // given
        setContent()

        // then
        rule.onNodeWithTag(
            DetailScreenLoadingStateTags.LOTTIE.name,
        ).assertIsDisplayed()
    }
}
