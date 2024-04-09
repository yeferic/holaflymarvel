package com.yeferic.holaflymarvel.data.repositories

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.yeferic.holaflymarvel.data.sources.remote.ComicApi
import com.yeferic.holaflymarvel.data.sources.remote.dto.BaseResponseDto
import com.yeferic.holaflymarvel.data.sources.remote.dto.ComicDetailDto
import com.yeferic.holaflymarvel.data.sources.remote.dto.ComicDto
import com.yeferic.holaflymarvel.data.sources.remote.dto.Result
import com.yeferic.holaflymarvel.data.sources.remote.dto.ThumbnailDto
import com.yeferic.holaflymarvel.data.sources.remote.dto.mapToDomain
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class ComicsRepositoryImplTest {
    private lateinit var repositoryImpl: ComicsRepositoryImpl

    @MockK
    private lateinit var apiMock: ComicApi

    @MockK(relaxed = true, relaxUnitFun = true)
    private lateinit var gsonMock: Gson

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repositoryImpl =
            ComicsRepositoryImpl(
                api = apiMock,
                gson = gsonMock,
            )
    }

    @After
    fun tearDown() {
        confirmVerified(
            apiMock,
            gsonMock,
        )
    }

    @Test
    fun `map any response from api`() =
        runTest {
            // given
            val response = mockk<Response<BaseResponseDto>>()
            val baseDto = mockk<BaseResponseDto>()
            val resultDto = mockk<Result>()
            val comicDto =
                ComicDto(
                    0,
                    ThumbnailDto("Path", "JPG"),
                )

            val jsonElement = mockk<JsonElement>()
            val arrayJson = JsonArray().apply { add(jsonElement) }
            val listType = object : TypeToken<List<ComicDto>>() {}.type

            val dtos = listOf(comicDto)
            val comics = listOf(comicDto.mapToDomain())

            coEvery {
                gsonMock.fromJson<List<ComicDto>?>(arrayJson, listType)
            } returns dtos

            coEvery {
                resultDto.results
            } returns arrayJson

            coEvery {
                baseDto.data
            } returns resultDto

            coEvery {
                response.body()
            } returns baseDto

            // when
            val listResult = repositoryImpl.mapResponse(response)

            // then
            coVerify(exactly = 1) {
                gsonMock.fromJson<List<ComicDto>?>(arrayJson, listType)
                baseDto.data
                response.body()
                resultDto.results
            }
            Assert.assertEquals(listResult, comics)
            confirmVerified()
        }

    @Test
    fun `call to get comics then return comics list`() =
        runTest {
            // given
            val response = mockk<Response<BaseResponseDto>>()
            val baseDto = mockk<BaseResponseDto>()
            val resultDto = mockk<Result>()
            val comicDto =
                ComicDto(
                    0,
                    ThumbnailDto("Path", "JPG"),
                )

            val jsonElement = mockk<JsonElement>()
            val arrayJson = JsonArray().apply { add(jsonElement) }
            val listType = object : TypeToken<List<ComicDto>>() {}.type

            val dtos = listOf(comicDto)
            val comics = listOf(comicDto.mapToDomain())

            val id = 1L
            val offset = 0
            val limit = 30

            coEvery {
                gsonMock.fromJson<List<ComicDto>?>(arrayJson, listType)
            } returns dtos

            coEvery {
                resultDto.results
            } returns arrayJson

            coEvery {
                baseDto.data
            } returns resultDto

            coEvery {
                response.body()
            } returns baseDto

            coEvery {
                apiMock.getComics(id = id, offset = offset, limit = limit)
            } returns response

            // when
            val listResult = repositoryImpl.getComics(id = id, offset = offset, limit = limit)

            // then
            coVerify(exactly = 1) {
                gsonMock.fromJson<List<ComicDto>?>(arrayJson, listType)
                baseDto.data
                response.body()
                resultDto.results
                apiMock.getComics(id = id, offset = offset, limit = limit)
            }
            Assert.assertEquals(listResult, comics)
            confirmVerified()
        }

    @Test
    fun `call to get comic detail then return comic info`() =
        runTest {
            // given
            val id = 1L

            val response = mockk<Response<BaseResponseDto>>()
            val baseDto = mockk<BaseResponseDto>()
            val resultDto = mockk<Result>()
            val comicDetailDto =
                ComicDetailDto(
                    0,
                    "Name",
                    "Description",
                    ThumbnailDto("Path", "JPG"),
                )

            val jsonElement = mockk<JsonElement>()
            val arrayJson = JsonArray().apply { add(jsonElement) }

            val detail = comicDetailDto.mapToDomain()

            coEvery {
                gsonMock.fromJson(arrayJson.first(), ComicDetailDto::class.java)
            } returns comicDetailDto

            coEvery {
                resultDto.results
            } returns arrayJson

            coEvery {
                baseDto.data
            } returns resultDto

            coEvery {
                response.body()
            } returns baseDto

            coEvery {
                apiMock.getComicDetail(id)
            } returns response

            // when
            val detailResponse = repositoryImpl.getComicDetail(id)

            // then
            coVerify(exactly = 1) {
                gsonMock.fromJson(arrayJson.first(), ComicDetailDto::class.java)
                baseDto.data
                response.body()
                resultDto.results
                apiMock.getComicDetail(id)
            }
            Assert.assertEquals(detailResponse, detail)
            confirmVerified()
        }
}
