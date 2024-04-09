package com.yeferic.holaflymarvel.presentation.menu.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class MenuScreenLoadingStateKtTest {
    @get:Rule
    val rule = createComposeRule()

    private fun setContent() {
        rule.setContent {
            MenuScreenLoadingState()
        }
    }

    @Test
    fun validateLottieAnimation() {
        // given
        setContent()

        // then
        rule.onNodeWithTag(
            MenuScreenLoadingStateTags.LOTTIE.name,
        ).assertIsDisplayed()
    }
}
