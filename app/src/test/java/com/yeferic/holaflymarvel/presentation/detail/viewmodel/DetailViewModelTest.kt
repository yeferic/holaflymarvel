package com.yeferic.holaflymarvel.presentation.detail.viewmodel

import com.yeferic.holaflymarvel.commons.getPrivatePropertyValue
import com.yeferic.holaflymarvel.commons.setPrivatePropertyValue
import com.yeferic.holaflymarvel.core.commons.Constants
import com.yeferic.holaflymarvel.core.commons.UseCaseStatus
import com.yeferic.holaflymarvel.data.sources.remote.exceptions.ComicsApiError
import com.yeferic.holaflymarvel.domain.models.ComicDetail
import com.yeferic.holaflymarvel.domain.usecases.comics.GetComicDetailUseCase
import com.yeferic.holaflymarvel.presentation.detail.states.ComicDetailUiState
import com.yeferic.holaflymarvel.presentation.menu.states.MenuUiStates
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {
    @MockK
    private lateinit var viewModel: DetailViewModel

    @MockK(relaxed = true)
    private lateinit var comicDetailUseCaseMock: GetComicDetailUseCase

    companion object {
        private const val UI_STATE_FIELD = "_uiState"
        private const val COMIC_ID = "idComic"
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = DetailViewModel(comicDetailUseCaseMock)
    }

    @After
    fun tearDown() {
        confirmVerified(comicDetailUseCaseMock)
    }

    @Test
    fun `set id comic than change current value`() =
        runTest {
            // given
            val id = 123L

            // when
            viewModel.setIdComic(id)
            val idComic =
                getPrivatePropertyValue<Long>(viewModel, COMIC_ID)

            // then
            Assert.assertEquals(idComic, id)
        }

    @Test
    fun `get comic info then returns loading state`() =
        runTest {
            // given
            val id = 123L
            val loadingState = UseCaseStatus.Loading
            val flowResponse = flow { emit(loadingState) }

            coEvery {
                comicDetailUseCaseMock(id)
            } returns flowResponse

            setPrivatePropertyValue(viewModel, COMIC_ID, id)

            // when
            viewModel.getComicInfo()
            val uiState =
                getPrivatePropertyValue<MutableStateFlow<ComicDetailUiState>>(
                    viewModel,
                    UI_STATE_FIELD,
                )

            // then
            coVerify {
                comicDetailUseCaseMock(id)
            }
            Assert.assertEquals(uiState.value, ComicDetailUiState.Loading)
        }

    @Test
    fun `get character info then returns data loaded state`() =
        runTest {
            // given
            val id = 123L
            val detail = ComicDetail()
            val state = UseCaseStatus.Success(detail)
            val flowResponse = flow { emit(state) }

            coEvery {
                comicDetailUseCaseMock(id)
            } returns flowResponse

            setPrivatePropertyValue(viewModel, COMIC_ID, id)

            // when
            viewModel.getComicInfo()
            val uiState =
                getPrivatePropertyValue<MutableStateFlow<ComicDetailUiState>>(
                    viewModel,
                    UI_STATE_FIELD,
                )

            // then
            coVerify {
                comicDetailUseCaseMock(id)
            }
            Assert.assertEquals(uiState.value, ComicDetailUiState.DataLoaded(detail))
        }

    @Test
    fun `get character info then returns error state`() =
        runTest {
            // given
            val id = 123L
            val error = ComicsApiError.ServiceError(Constants.EMPTY_STRING)
            val state = UseCaseStatus.Error(error)
            val flowResponse = flow { emit(state) }

            coEvery {
                comicDetailUseCaseMock(id)
            } returns flowResponse

            setPrivatePropertyValue(viewModel, COMIC_ID, id)

            // when
            viewModel.getComicInfo()
            val uiState =
                getPrivatePropertyValue<MutableStateFlow<ComicDetailUiState>>(
                    viewModel,
                    UI_STATE_FIELD,
                )

            // then
            coVerify {
                comicDetailUseCaseMock(id)
            }
            Assert.assertEquals(uiState.value, ComicDetailUiState.Error(error))
        }

    @Test
    fun `set ui state then change ui state value`() =
        runTest {
            // given
            val state = ComicDetailUiState.Loading

            // when
            viewModel.setUiState(state)
            val uiState =
                getPrivatePropertyValue<MutableStateFlow<MenuUiStates>>(viewModel, UI_STATE_FIELD)

            // then
            Assert.assertEquals(uiState.value, state)
        }
}
