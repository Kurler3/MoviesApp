package com.miguel.moviesapp.di

import com.miguel.moviesapp.MoviesApiApplicationClass
import com.miguel.moviesapp.api.MovieAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(MoviesApiApplicationClass::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit =
        Retrofit.Builder()
            .baseUrl(MovieAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideMovieAPI(retrofit: Retrofit ) : MovieAPI =
        retrofit.create(MovieAPI::class.java)
}