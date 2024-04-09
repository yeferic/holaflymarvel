package com.yeferic.holaflymarvel.domain.usecases.comics

import com.yeferic.holaflymarvel.core.commons.UseCaseStatus
import com.yeferic.holaflymarvel.data.sources.remote.exceptions.CharacterErrorMessage
import com.yeferic.holaflymarvel.data.sources.remote.exceptions.ComicsApiError
import com.yeferic.holaflymarvel.data.sources.remote.exceptions.ComicsErrorMapper
import com.yeferic.holaflymarvel.domain.models.Comic
import com.yeferic.holaflymarvel.domain.repositories.ComicsRepository
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
class GetComicsUseCaseTest {
    private lateinit var useCase: GetComicsUseCase

    @MockK
    private lateinit var repository: ComicsRepository

    @MockK
    private lateinit var errorMapper: ComicsErrorMapper

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase =
            GetComicsUseCase(
                repository = repository,
                errorMapper = errorMapper,
            )
    }

    @Test
    fun `invoke then return loading and success state`() =
        runTest {
            // given
            val listComics = listOf(Comic())
            val id: Long = 0
            val limit = GetComicsUseCase.MAX_LIMIT
            val offset = 0

            coEvery {
                repository.getComics(id = id, limit = limit, offset = offset)
            } returns listComics

            // when
            val flowsResponse = useCase.invoke(id = id, currentOffset = offset).toList()

            // then
            coVerify {
                repository.getComics(id = id, limit = limit, offset = offset)
            }
            Assert.assertEquals(flowsResponse.size, 2)
            Assert.assertEquals(flowsResponse[0], UseCaseStatus.Loading)
            Assert.assertEquals(flowsResponse[1], UseCaseStatus.Success(listComics))
            confirmVerified()
        }

    @Test
    fun `invoke then return loading and error state`() =
        runTest {
            // given
            val errorEntity =
                ComicsApiError.ServiceError(
                    CharacterErrorMessage.GENERAL_EXCEPTION.message,
                )
            val id: Long = 0
            val limit = GetComicsUseCase.MAX_LIMIT
            val offset = 0
            val error = Exception()
            coEvery {
                errorMapper.getError(error)
            } returns errorEntity

            coEvery {
                repository.getComics(id = id, limit = limit, offset = offset)
            } throws error

            // when
            val flowsResponse = useCase.invoke(id = id, currentOffset = offset).toList()

            // then
            coVerify {
                repository.getComics(id = id, limit = limit, offset = offset)
                errorMapper.getError(error)
            }
            Assert.assertEquals(flowsResponse.size, 2)
            Assert.assertEquals(flowsResponse[0], UseCaseStatus.Loading)
            Assert.assertEquals(flowsResponse[1], UseCaseStatus.Error(errorEntity))
            confirmVerified()
        }
}
