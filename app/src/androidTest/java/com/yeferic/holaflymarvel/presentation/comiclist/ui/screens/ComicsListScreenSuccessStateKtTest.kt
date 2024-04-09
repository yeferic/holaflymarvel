package com.yeferic.holaflymarvel.presentation.comiclist.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.yeferic.holaflymarvel.domain.models.Comic
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class ComicsListScreenSuccessStateKtTest {
    @get:Rule
    val rule = createComposeRule()

    private fun setContent(
        list: List<Comic> = listOf(),
        loadingMore: Boolean = false,
        loadingMoreFn: () -> Unit = {},
        goToFn: (Long) -> Unit = {},
    ) {
        rule.setContent {
            val params =
                ComicsListScreenSuccessStateParams(list, loadingMore, loadingMoreFn, goToFn)
            ComicsListScreenSuccessState(params)
        }
    }

    @Test
    fun validateListVisibility() {
        // given
        setContent(listOf(Comic()))

        // then
        rule.onNodeWithTag(
            ComicsListScreenSuccessStateTags.LAZY_LIST.name,
        ).assertIsDisplayed()
    }

    @Test
    fun validateItemListOnClickListener() {
        // given
        setContent(listOf(Comic(id = 5))) {
            Assert.assertEquals(5L, it)
        }

        // then
        rule.onNodeWithTag(
            ComicsListScreenSuccessStateTags.ITEM_LIST.name,
        ).performClick()
    }
}
