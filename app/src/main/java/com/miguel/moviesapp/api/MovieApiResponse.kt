package com.miguel.moviesapp.api

import com.miguel.moviesapp.data.Movie

data class MovieApiResponse (
    val results : List<Movie>
)