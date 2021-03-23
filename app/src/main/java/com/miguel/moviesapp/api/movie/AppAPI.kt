package com.miguel.moviesapp.api.movie

import retrofit2.http.GET
import retrofit2.http.Query

interface AppAPI {

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
    suspend fun searchMoviesPopular(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("language") language: String?,
        @Query("region") region: String?
    ) : MovieApiResponse

    @GET("search/tv")
    suspend fun searchSeries(
            @Query("api_key") apiKey: String,
            @Query("query") query: String,
            @Query("page") page: Int,
            @Query("language") language: String?,
            @Query("include_adult") includeAdult: Boolean?,
            @Query("first_air_date_year") year: Int?
    ) : SeriesApiResponse

    @GET("tv/popular")
    suspend fun searchSeriesPopular(
            @Query("api_key") apiKey: String,
            @Query("page") page: Int,
            @Query("language") language: String?
    ) : SeriesApiResponse

    @GET("genre/movie/list")
    suspend fun searchMoviesGenres(
        @Query("api_key") apiKey: String,
    ) : MoviesGenresApiResponse

    @GET("genre/tv/list")
    suspend fun searchSeriesGenres(
        @Query("api_key") apiKey: String,
    ) : SeriesGenresApiResponse

}