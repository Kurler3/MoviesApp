package com.miguel.moviesapp.ui.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.miguel.moviesapp.api.movie.AppAPI
import com.miguel.moviesapp.api.movie.MoviesGenresApiResponse
import com.miguel.moviesapp.ui.filters.MovieFilter
import com.miguel.moviesapp.data.AppRepository
import com.miguel.moviesapp.data.Movie
import com.miguel.moviesapp.data.MovieGenres
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

class MoviesViewModel @ViewModelInject constructor(
    private val movieRepository: AppRepository,
    private val appAPI: AppAPI)
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