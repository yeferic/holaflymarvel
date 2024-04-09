package com.yeferic.holaflymarvel.domain.usecases.character

import com.yeferic.holaflymarvel.core.commons.UseCaseStatus
import com.yeferic.holaflymarvel.data.sources.remote.exceptions.CharacterApiError
import com.yeferic.holaflymarvel.data.sources.remote.exceptions.CharacterErrorMapper
import com.yeferic.holaflymarvel.data.sources.remote.exceptions.CharacterErrorMessage
import com.yeferic.holaflymarvel.domain.models.Character
import com.yeferic.holaflymarvel.domain.repositories.CharacterRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetCharactersUseCaseTest {
    private lateinit var useCase: GetCharactersUseCase

    @MockK
    private lateinit var repository: CharacterRepository

    @MockK
    private lateinit var errorMapper: CharacterErrorMapper

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase =
            GetCharactersUseCase(
                repository = repository,
                errorMapper = errorMapper,
            )
    }

    @Test
    fun `invoke then return loading and success state`() =
        runTest {
            // given
            val responseList = listOf(Character())

            coEvery {
                repository.getCharacters()
            } returns responseList

            // when
            val flowsResponse = useCase.invoke().toList()

            // then
            coVerify {
                repository.getCharacters()
            }
            Assert.assertEquals(flowsResponse.size, 2)
            Assert.assertEquals(flowsResponse[0], UseCaseStatus.Loading)
            Assert.assertEquals(flowsResponse[1], UseCaseStatus.Success(responseList))
            confirmVerified()
        }

    @Test
    fun `invoke then return loading and error state`() =
        runTest {
            // given
            val errorEntity =
                CharacterApiError.ServiceError(
                    CharacterErrorMessage.GENERAL_EXCEPTION.message,
                )
            val error = Exception()
            coEvery {
                errorMapper.getError(error)
            } returns errorEntity

            coEvery {
                repository.getCharacters()
            } throws error

            // when
            val flowsResponse = useCase.invoke().toList()

            // then
            coVerify {
                repository.getCharacters()
                errorMapper.getError(error)
            }
            Assert.assertEquals(flowsResponse.size, 2)
            Assert.assertEquals(flowsResponse[0], UseCaseStatus.Loading)
            Assert.assertEquals(flowsResponse[1], UseCaseStatus.Error(errorEntity))
            confirmVerified()
        }
}
