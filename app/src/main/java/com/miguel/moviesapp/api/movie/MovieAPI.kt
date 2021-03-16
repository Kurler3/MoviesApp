package com.miguel.moviesapp.api.movie

import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val CLIENT_ID = "a09506d86e138c4076290056053b57d6"
    }
    @GET("search/movie")
    suspend fun searchMovies(
            @Query("api_key") api_key : String,
            @Query("query") query: String,
            @Query("page") page: Int,
            @Query("language") language : String?,
            @Query("include_adult") includeAdult: Boolean?,
            @Query("region") region: String?,
            @Query("year") year: Int?
    ) : MovieApiResponse

    @GET("movie/popular")
    suspend fun searchPopular(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("language") language: String?,
        @Query("region") region: String?
    ) : MovieApiResponse

}