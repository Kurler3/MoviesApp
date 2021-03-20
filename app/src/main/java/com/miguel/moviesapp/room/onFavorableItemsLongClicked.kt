package com.miguel.moviesapp.room

import com.miguel.moviesapp.data.Movie
import com.miguel.moviesapp.data.Serie

interface onFavorableItemsLongClicked {

    fun onMovieAddedToFavorites(movie: Movie?)
    fun onMovieRemovedFromFavorites(movie: Movie?)
    fun onSerieAddedToFavorites(serie: Serie?)
    fun onSerieRemovedFromFavorites(serie: Serie?)
}