package com.yeferic.holaflymarvel.di

import com.google.gson.Gson
import com.yeferic.holaflymarvel.core.commons.Constants
import com.yeferic.holaflymarvel.data.sources.remote.CharacterApi
import com.yeferic.holaflymarvel.data.sources.remote.ComicApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun providesRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.URL_SERVICE)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun providesOKHttpClient(): OkHttpClient {
        val apiKeyInterceptor =
            Interceptor { chain ->
                val url =
                    chain.request().url.newBuilder()
                        .addQueryParameter("ts", Constants.TS)
                        .addQueryParameter("apikey", Constants.API_KEY)
                        .addQueryParameter("hash", Constants.HASH)
                        .build()
                val request =
                    chain.request().newBuilder()
                        .url(url)
                        .build()
                chain.proceed(request)
            }

        return OkHttpClient()
            .newBuilder()
            .addInterceptor(apiKeyInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun providesCharacterApi(retrofit: Retrofit): CharacterApi {
        return retrofit.create(CharacterApi::class.java)
    }

    @Provides
    @Singleton
    fun providesComicsApi(retrofit: Retrofit): ComicApi {
        return retrofit.create(ComicApi::class.java)
    }
}
