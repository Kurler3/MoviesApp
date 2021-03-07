package com.miguel.moviesapp.ui.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.miguel.moviesapp.api.MovieQuery
import com.miguel.moviesapp.data.MoviesRepository

class MoviesViewModel @ViewModelInject constructor(
    private val movieRepository: MoviesRepository)
    : ViewModel(){

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    // Using MutableLiveData switchMap means that photos will change whenever the value of currentQuery changes. Can be thought
    // of like a listener for mutable live data
    val movies = currentQuery.switchMap {   query ->
        // Have to cache the live data otherwise the app will crash when rotating
        // because cant load from the same page data twice
        movieRepository.getPopularMovies().cachedIn(viewModelScope)
    }

    fun searchMovies(movieQuery: String){
        currentQuery.value = movieQuery
    }

    companion object {
        private val DEFAULT_QUERY = "Superman"
    }
}