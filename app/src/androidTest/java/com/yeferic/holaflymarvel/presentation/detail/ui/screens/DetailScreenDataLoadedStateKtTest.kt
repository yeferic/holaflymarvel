package com.yeferic.holaflymarvel.presentation.detail.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.yeferic.holaflymarvel.domain.models.ComicDetail
import org.junit.Rule
import org.junit.Test

class DetailScreenDataLoadedStateKtTest {
    @get:Rule
    val rule = createComposeRule()

    private fun setContent(comicDetail: ComicDetail = ComicDetail()) {
        rule.setContent {
            DetailScreenDataLoadedState(comicDetail)
        }
    }

    @Test
    fun validateListVisibility() {
        // given
        setContent(ComicDetail(id = 1L, title = "TITLE"))

        // then
        rule.onNodeWithTag(
            DetailScreenDataLoadedStateTags.LAZY_LIST.name,
        ).assertIsDisplayed()
    }

    @Test
    fun validateItemListVisibility() {
        // given
        setContent(
            ComicDetail(id = 1L, title = "TITLE", description = "DESCRIPTION", imageUrl = "www"),
        )

        // then
        rule.onNodeWithTag(
            DetailScreenDataLoadedStateTags.ITEM_IMAGE.name,
        ).assertIsDisplayed()

        rule.onNodeWithTag(
            DetailScreenDataLoadedStateTags.ITEM_TITLE.name,
        ).assertIsDisplayed()

        rule.onNodeWithTag(
            DetailScreenDataLoadedStateTags.ITEM_DESCRIPTION.name,
        ).assertIsDisplayed()
    }
}
