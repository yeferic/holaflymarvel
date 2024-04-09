package com.yeferic.holaflymarvel.presentation.comiclist.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class ComicsListScreenLoadingStateKtTest {
    @get:Rule
    val rule = createComposeRule()

    private fun setContent() {
        rule.setContent {
            ComicsListScreenLoadingState()
        }
    }

    @Test
    fun validateLottieAnimation() {
        // given
        setContent()

        // then
        rule.onNodeWithTag(
            ComicsListScreenLoadingStateTags.LOTTIE.name,
        ).assertIsDisplayed()
    }
}
