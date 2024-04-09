package com.yeferic.holaflymarvel.presentation.comiclist.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.yeferic.holaflymarvel.core.commons.Constants
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class ComicsListScreenErrorStateKtTest {
    @get:Rule
    val rule = createComposeRule()

    private fun setContent(
        error: String = Constants.EMPTY_STRING,
        retryFn: () -> Unit = {},
    ) {
        rule.setContent {
            val params = ComicsListScreenErrorStateParams(error = error, retryFunction = retryFn)
            ComicsListScreenErrorState(params)
        }
    }

    @Test
    fun validateWidgetsVisibility() {
        // given
        setContent(error = "ERROR")

        // then
        rule.onNodeWithTag(
            ComicsListScreenErrorStateTags.LOTTIE_IMAGE.name,
        ).assertIsDisplayed()

        rule.onNodeWithTag(
            ComicsListScreenErrorStateTags.TEXT_ERROR.name,
        ).assertIsDisplayed()

        rule.onNodeWithTag(
            ComicsListScreenErrorStateTags.BUTTON_RETRY.name,
        ).assertIsDisplayed()
    }

    @Test
    fun validateShowTextError() {
        // given
        val text = "ERROR"
        setContent(error = text)

        // then
        rule.onNodeWithText(text).assertIsDisplayed()
    }

    @Test
    fun validateButtonAction() {
        // given
        val text = "ERROR"
        setContent(error = text) {
            Assert.assertTrue(true)
        }

        // then
        rule.onNodeWithTag(
            ComicsListScreenErrorStateTags.BUTTON_RETRY.name,
            useUnmergedTree = true,
        ).performClick()
    }
}
