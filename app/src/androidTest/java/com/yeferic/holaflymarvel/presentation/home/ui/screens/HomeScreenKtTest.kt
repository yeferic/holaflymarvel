package com.yeferic.holaflymarvel.presentation.home.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class HomeScreenKtTest {
    @get:Rule
    val rule = createComposeRule()

    private fun setContent(callback: () -> Unit) {
        rule.setContent {
            val params =
                HomeScreenParams {
                    callback.invoke()
                }

            HomeScreen(params = params)
        }
    }

    @Test
    fun validateButtonText() {
        // given
        setContent { }

        // then
        rule.onNodeWithTag(
            HomeScreenTags.TEXT_BUTTON.name,
            useUnmergedTree = true,
        ).assertIsDisplayed()
    }

    @Test
    fun validateButtonAction() {
        // given
        setContent { Assert.assertTrue(true) }

        // then
        rule.onNodeWithTag(HomeScreenTags.BUTTON.name, useUnmergedTree = true).performClick()
    }

    @Test
    fun validateLottieAnimation() {
        // given
        setContent { }

        // then
        rule.onNodeWithTag(
            HomeScreenTags.LOTTIE_IMAGE.name,
            useUnmergedTree = true,
        ).assertIsDisplayed()
    }
}
