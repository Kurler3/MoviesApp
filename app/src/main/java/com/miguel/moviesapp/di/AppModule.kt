package com.miguel.moviesapp.di

import android.content.Context
import androidx.room.Room
import com.miguel.moviesapp.api.movie.AppAPI
import com.miguel.moviesapp.room.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit =
        Retrofit.Builder()
            .baseUrl(AppAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideMovieAPI(retrofit: Retrofit ) : AppAPI =
        retrofit.create(AppAPI::class.java)

    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "AppDatabase"
    ).build() // The reason we can construct a database for the repo

    @Singleton
    @Provides
    fun provideMoviesDao(db: AppDatabase) = db.getMoviesDao()

    @Singleton
    @Provides
    fun provideSeriesDao(db: AppDatabase) = db.getSeriesDao()
}