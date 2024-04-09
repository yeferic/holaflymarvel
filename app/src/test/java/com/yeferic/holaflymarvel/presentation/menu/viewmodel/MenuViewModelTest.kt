package com.yeferic.holaflymarvel.presentation.menu.viewmodel

import com.yeferic.holaflymarvel.commons.getPrivatePropertyValue
import com.yeferic.holaflymarvel.core.commons.Constants.EMPTY_STRING
import com.yeferic.holaflymarvel.core.commons.UseCaseStatus
import com.yeferic.holaflymarvel.data.sources.remote.exceptions.CharacterApiError
import com.yeferic.holaflymarvel.domain.models.Character
import com.yeferic.holaflymarvel.domain.usecases.character.GetCharactersUseCase
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
class MenuViewModelTest {
    @MockK
    private lateinit var viewModel: MenuViewModel

    @MockK(relaxed = true)
    private lateinit var charactersUseCaseMock: GetCharactersUseCase

    companion object {
        private const val UI_STATE_FIELD = "_uiState"
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = MenuViewModel(charactersUseCaseMock)
    }

    @After
    fun tearDown() {
        confirmVerified(charactersUseCaseMock)
    }

    @Test
    fun `get character info then returns loading state`() =
        runTest {
            // given
            val loadingState = UseCaseStatus.Loading
            val flowResponse = flow { emit(loadingState) }

            coEvery {
                charactersUseCaseMock()
            } returns flowResponse

            // when
            viewModel.getCharacterInfo()
            val uiState =
                getPrivatePropertyValue<MutableStateFlow<MenuUiStates>>(viewModel, UI_STATE_FIELD)

            // then
            coVerify {
                charactersUseCaseMock()
            }
            Assert.assertEquals(uiState.value, MenuUiStates.Loading)
        }

    @Test
    fun `get character info then returns data loaded state`() =
        runTest {
            // given
            val listResponse = listOf(Character())
            val state = UseCaseStatus.Success(listResponse)
            val flowResponse = flow { emit(state) }

            coEvery {
                charactersUseCaseMock()
            } returns flowResponse

            // when
            viewModel.getCharacterInfo()
            val uiState =
                getPrivatePropertyValue<MutableStateFlow<MenuUiStates>>(viewModel, UI_STATE_FIELD)

            // then
            coVerify {
                charactersUseCaseMock()
            }
            Assert.assertEquals(uiState.value, MenuUiStates.DataLoaded(listResponse))
        }

    @Test
    fun `get character info then returns error state`() =
        runTest {
            // given
            val error = CharacterApiError.ServiceError(EMPTY_STRING)
            val state = UseCaseStatus.Error(error)
            val flowResponse = flow { emit(state) }

            coEvery {
                charactersUseCaseMock()
            } returns flowResponse

            // when
            viewModel.getCharacterInfo()
            val uiState =
                getPrivatePropertyValue<MutableStateFlow<MenuUiStates>>(viewModel, UI_STATE_FIELD)

            // then
            coVerify {
                charactersUseCaseMock()
            }
            Assert.assertEquals(uiState.value, MenuUiStates.Error(error))
        }

    @Test
    fun `set ui state then change ui state value`() =
        runTest {
            // given
            val state = MenuUiStates.Loading

            // when
            viewModel.setUiState(state)
            val uiState =
                getPrivatePropertyValue<MutableStateFlow<MenuUiStates>>(viewModel, UI_STATE_FIELD)

            // then
            coVerify {
                charactersUseCaseMock()
            }
            Assert.assertEquals(uiState.value, state)
        }
}
