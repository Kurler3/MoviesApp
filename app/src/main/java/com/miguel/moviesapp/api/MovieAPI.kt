package com.miguel.moviesapp.api

import androidx.viewbinding.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val CLIENT_ID = "a09506d86e138c4076290056053b57d6"
    }

    @GET("movie/popular")
    suspend fun searchPopular(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ) : MovieApiResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") api_key : String,
        @Query("query") query: String,
        @Query("page") page: Int
    ) : MovieApiResponse

    @GET("search/movie")
    suspend fun searchMoviesByLanguage(
        @Query("api_key") api_key : String,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("language") language : String,
    ) : MovieApiResponse

    @GET("search/movie")
    suspend fun searchMoviesByBeingAdult(
        @Query("api_key") api_key : String,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("include_adult") includeAdult: Boolean,
    ) : MovieApiResponse

    @GET("search/movie")
    suspend fun searchMoviesByRegion(
        @Query("api_key") api_key : String,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("region") region: String,
    ) : MovieApiResponse

    @GET("search/movie")
    suspend fun searchMoviesByYear(
        @Query("api_key") api_key : String,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("year") year: Int,
    ) : MovieApiResponse

    @GET("search/movie")
    suspend fun searchMoviesByReleaseYear(
        @Query("api_key") api_key : String,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("primary_release_year") primaryReleaseYear: Int,
    ) : MovieApiResponse

}