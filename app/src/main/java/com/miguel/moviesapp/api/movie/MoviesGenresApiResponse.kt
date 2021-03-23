package com.miguel.moviesapp.api.movie

import com.miguel.moviesapp.data.MovieGenres

data class MoviesGenresApiResponse(
    val genres : List<MovieGenres>
)
