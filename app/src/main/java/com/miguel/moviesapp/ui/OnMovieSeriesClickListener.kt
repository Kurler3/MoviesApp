package com.miguel.moviesapp.ui

import com.miguel.moviesapp.data.Movie
import com.miguel.moviesapp.data.Serie

interface OnMovieSeriesClickListener {
    fun onMovieClicked(movie: Movie?)
    fun onSeriesClicked(series: Serie?)
}