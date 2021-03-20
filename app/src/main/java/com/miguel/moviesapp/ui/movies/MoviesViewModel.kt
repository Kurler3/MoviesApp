package com.miguel.moviesapp.ui.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.miguel.moviesapp.ui.filters.MovieFilter
import com.miguel.moviesapp.data.AppRepository
import com.miguel.moviesapp.data.Movie
import kotlinx.coroutines.launch

class MoviesViewModel @ViewModelInject constructor(
    private val movieRepository: AppRepository)
    : ViewModel(){

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    // Using MutableLiveData switchMap means that photos will change whenever the value of currentQuery changes. Can be thought
    // of like a listener for mutable live data
    val movies = currentQuery.switchMap { filter ->
        // Have to cache the live data otherwise the app will crash when rotating
        // because cant load from the same page data twice
        movieRepository.searchMovies(filter).cachedIn(viewModelScope)
    }

    fun searchMovies(filter: MovieFilter){
        currentQuery.value = filter
    }

    fun addMovieToFavorites(movie: Movie) = viewModelScope.launch {
        movieRepository.insertMovie(movie)
    }

    companion object {
        private val DEFAULT_QUERY = MovieFilter(null, "en-US",
            true, null, null)
    }
}