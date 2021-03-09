package com.miguel.moviesapp.ui.filters

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.miguel.moviesapp.api.MovieFilter

class MovieFilterViewModel @ViewModelInject constructor(): ViewModel(){

    // Need to get the MovieFilter that the MoviesListFragment was using when this was launched and assign it here
    private var movieFilter : MovieFilter? = null

    val LANGUAGE_FILTERS = arrayOf("")
    val COUNTRY_FILTERS = arrayOf("")
    val YEAR_FILTERS = arrayOf("")
}