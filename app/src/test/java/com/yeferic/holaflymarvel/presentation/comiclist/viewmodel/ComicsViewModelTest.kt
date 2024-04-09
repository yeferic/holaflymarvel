package com.yeferic.holaflymarvel.presentation.comiclist.viewmodel

import com.yeferic.holaflymarvel.commons.getPrivatePropertyValue
import com.yeferic.holaflymarvel.commons.setPrivatePropertyValue
import com.yeferic.holaflymarvel.core.commons.Constants
import com.yeferic.holaflymarvel.core.commons.UseCaseStatus
import com.yeferic.holaflymarvel.data.sources.remote.exceptions.ComicsApiError
import com.yeferic.holaflymarvel.domain.models.Comic
import com.yeferic.holaflymarvel.domain.usecases.comics.GetComicsUseCase
import com.yeferic.holaflymarvel.presentation.comiclist.states.ComicsListUiState
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
class ComicsViewModelTest {
    @MockK
    private lateinit var viewModel: ComicsViewModel

    @MockK(relaxed = true)
    private lateinit var comicsUseCaseMock: GetComicsUseCase

    companion object {
        private const val UI_STATE_FIELD = "_uiState"
        private const val ID_CHARACTER_FIELD = "idCharacter"
        private const val CURRENT_OFFSET_FIELD = "currentOffset"
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = ComicsViewModel(comicsUseCaseMock)
    }

    @After
    fun tearDown() {
        confirmVerified(comicsUseCaseMock)
    }

    @Test
    fun `set id character than change current value`() =
        runTest {
            // given
            val id = 123L

            // when
            viewModel.setIdCharacter(id)
            val idComic =
                getPrivatePropertyValue<Long>(viewModel, ID_CHARACTER_FIELD)

            // then
            Assert.assertEquals(idComic, id)
        }

    @Test
    fun `get comics with load more false then returns loading state`() =
        runTest {
            // given
            val id = 123L
            val offset = 10
            val loadingState = UseCaseStatus.Loading
            val flowResponse = flow { emit(loadingState) }

            coEvery {
                comicsUseCaseMock(id, offset)
            } returns flowResponse

            setPrivatePropertyValue(viewModel, ID_CHARACTER_FIELD, id)
            setPrivatePropertyValue(viewModel, CURRENT_OFFSET_FIELD, offset)

            // when
            viewModel.getComics()
            val uiState =
                getPrivatePropertyValue<MutableStateFlow<ComicsListUiState>>(
                    viewModel,
                    UI_STATE_FIELD,
                )

            // then
            coVerify {
                comicsUseCaseMock(id, offset)
            }
            Assert.assertEquals(uiState.value, ComicsListUiState.Loading)
        }

    @Test
    fun `get comics with load more true then returns success state in loading`() =
        runTest {
            // given
            val id = 123L
            val offset = 10
            val list = arrayListOf(Comic(), Comic())
            val state = UseCaseStatus.Loading
            val flowResponse = flow { emit(state) }
            val loadMore = true

            coEvery {
                comicsUseCaseMock(id, offset)
            } returns flowResponse

            setPrivatePropertyValue(
                viewModel,
                UI_STATE_FIELD,
                MutableStateFlow<ComicsListUiState>(
                    ComicsListUiState.Success(list, false),
                ),
            )
            setPrivatePropertyValue(viewModel, ID_CHARACTER_FIELD, id)
            setPrivatePropertyValue(viewModel, CURRENT_OFFSET_FIELD, offset)

            // when
            viewModel.getComics(loadMore)
            val uiState =
                getPrivatePropertyValue<MutableStateFlow<ComicsListUiState>>(
                    viewModel,
                    UI_STATE_FIELD,
                )
            val currentOffset =
                getPrivatePropertyValue<Int>(
                    viewModel,
                    CURRENT_OFFSET_FIELD,
                )

            // then
            coVerify {
                comicsUseCaseMock(id, offset)
            }
            Assert.assertEquals(uiState.value, ComicsListUiState.Success(list, loadMore))
            Assert.assertEquals(currentOffset, offset)
        }

    @Test
    fun `get comics with load more false then returns success state`() =
        runTest {
            // given
            val id = 123L
            val offset = 10
            val list = arrayListOf(Comic(), Comic())
            val state = UseCaseStatus.Success(list)
            val flowResponse = flow { emit(state) }
            val loadMore = false

            coEvery {
                comicsUseCaseMock(id, offset)
            } returns flowResponse

            setPrivatePropertyValue(viewModel, ID_CHARACTER_FIELD, id)
            setPrivatePropertyValue(viewModel, CURRENT_OFFSET_FIELD, offset)

            // when
            viewModel.getComics(loadMore)
            val uiState =
                getPrivatePropertyValue<MutableStateFlow<ComicsListUiState>>(
                    viewModel,
                    UI_STATE_FIELD,
                )

            // then
            coVerify {
                comicsUseCaseMock(id, offset)
            }
            Assert.assertEquals(uiState.value, ComicsListUiState.Success(list, loadMore))
        }

    @Test
    fun `get comics with load more true then returns success state`() =
        runTest {
            // given
            val id = 123L
            val offset = 10
            val list = arrayListOf(Comic(), Comic())
            val state = UseCaseStatus.Success(list)
            val flowResponse = flow { emit(state) }
            val loadMore = true

            coEvery {
                comicsUseCaseMock(id, offset)
            } returns flowResponse

            setPrivatePropertyValue(viewModel, ID_CHARACTER_FIELD, id)
            setPrivatePropertyValue(viewModel, CURRENT_OFFSET_FIELD, offset)
            setPrivatePropertyValue(
                viewModel,
                UI_STATE_FIELD,
                MutableStateFlow<ComicsListUiState>(
                    ComicsListUiState.Success(list, false),
                ),
            )

            // when
            viewModel.getComics(loadMore)
            val uiState =
                getPrivatePropertyValue<MutableStateFlow<ComicsListUiState>>(
                    viewModel,
                    UI_STATE_FIELD,
                )

            // then
            coVerify {
                comicsUseCaseMock(id, offset)
            }
            Assert.assertEquals(uiState.value, ComicsListUiState.Success(list, !loadMore))
        }

    @Test
    fun `get character info then returns error state`() =
        runTest {
            // given
            val id = 123L
            val offset = 10
            val error = ComicsApiError.ServiceError(Constants.EMPTY_STRING)
            val state = UseCaseStatus.Error(error)
            val flowResponse = flow { emit(state) }
            val loadMore = false

            coEvery {
                comicsUseCaseMock(id, offset)
            } returns flowResponse

            setPrivatePropertyValue(viewModel, ID_CHARACTER_FIELD, id)
            setPrivatePropertyValue(viewModel, CURRENT_OFFSET_FIELD, offset)

            // when
            viewModel.getComics(loadMore)
            val uiState =
                getPrivatePropertyValue<MutableStateFlow<ComicsListUiState>>(
                    viewModel,
                    UI_STATE_FIELD,
                )

            // then
            coVerify {
                comicsUseCaseMock(id, offset)
            }
            Assert.assertEquals(uiState.value, ComicsListUiState.Error(error))
        }

    @Test
    fun `set ui state then change ui state value`() =
        runTest {
            // given
            val state = ComicsListUiState.Loading

            // when
            viewModel.setUiState(state)
            val uiState =
                getPrivatePropertyValue<MutableStateFlow<ComicsListUiState>>(
                    viewModel,
                    UI_STATE_FIELD,
                )

            // then
            Assert.assertEquals(uiState.value, state)
        }
}
