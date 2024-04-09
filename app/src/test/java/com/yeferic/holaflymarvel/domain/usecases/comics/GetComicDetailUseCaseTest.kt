package com.yeferic.holaflymarvel.domain.usecases.comics

import com.yeferic.holaflymarvel.core.commons.UseCaseStatus
import com.yeferic.holaflymarvel.data.sources.remote.exceptions.CharacterErrorMessage
import com.yeferic.holaflymarvel.data.sources.remote.exceptions.ComicsApiError
import com.yeferic.holaflymarvel.data.sources.remote.exceptions.ComicsErrorMapper
import com.yeferic.holaflymarvel.domain.models.ComicDetail
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
class GetComicDetailUseCaseTest {
    private lateinit var useCase: GetComicDetailUseCase

    @MockK
    private lateinit var repository: ComicsRepository

    @MockK
    private lateinit var errorMapper: ComicsErrorMapper

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase =
            GetComicDetailUseCase(
                repository = repository,
                errorMapper = errorMapper,
            )
    }

    @Test
    fun `invoke then return loading and success state`() =
        runTest {
            // given
            val detail = ComicDetail()
            val id: Long = 0

            coEvery {
                repository.getComicDetail(id)
            } returns detail

            // when
            val flowsResponse = useCase.invoke(id).toList()

            // then
            coVerify {
                repository.getComicDetail(id)
            }
            Assert.assertEquals(flowsResponse.size, 2)
            Assert.assertEquals(flowsResponse[0], UseCaseStatus.Loading)
            Assert.assertEquals(flowsResponse[1], UseCaseStatus.Success(detail))
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
            val error = Exception()
            coEvery {
                errorMapper.getError(error)
            } returns errorEntity

            coEvery {
                repository.getComicDetail(id)
            } throws error

            // when
            val flowsResponse = useCase.invoke(id).toList()

            // then
            coVerify {
                repository.getComicDetail(id)
                errorMapper.getError(error)
            }
            Assert.assertEquals(flowsResponse.size, 2)
            Assert.assertEquals(flowsResponse[0], UseCaseStatus.Loading)
            Assert.assertEquals(flowsResponse[1], UseCaseStatus.Error(errorEntity))
            confirmVerified()
        }
}
