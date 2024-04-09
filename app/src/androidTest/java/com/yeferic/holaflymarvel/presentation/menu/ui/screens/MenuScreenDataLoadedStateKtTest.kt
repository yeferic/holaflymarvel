package com.yeferic.holaflymarvel.presentation.menu.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.yeferic.holaflymarvel.domain.models.Character
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class MenuScreenDataLoadedStateKtTest {
    @get:Rule
    val rule = createComposeRule()

    private fun setContent(
        list: List<Character> = listOf(),
        onItemClick: (Long) -> Unit = {},
    ) {
        rule.setContent {
            val params = MenuScreenDataLoadedParams(list, onItemClick)
            MenuScreenDataLoadedState(params)
        }
    }

    @Test
    fun validateListVisibility() {
        // given
        setContent(listOf(Character()))

        // then
        rule.onNodeWithTag(
            MenuScreenDataLoadedStateTags.LAZY_LIST.name,
        ).assertIsDisplayed()
    }

    @Test
    fun validateItemListVisibility() {
        // given
        setContent(listOf(Character()))

        // then
        rule.onNodeWithTag(
            MenuScreenDataLoadedStateTags.ITEM_LIST.name,
        ).assertIsDisplayed()
    }

    @Test
    fun validateItemListOnClickListener() {
        // given
        setContent(listOf(Character())) {
            Assert.assertTrue(true)
        }

        // then
        rule.onNodeWithTag(
            MenuScreenDataLoadedStateTags.ITEM_LIST.name,
        ).performClick()
    }
}
