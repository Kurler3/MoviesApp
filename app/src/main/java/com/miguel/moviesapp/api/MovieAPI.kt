package com.miguel.moviesapp.api

import retrofit2.http.Query

interface MovieAPI {

    suspend fun searchMovies(
        @Query()
    )
}