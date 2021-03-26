package com.miguel.moviesapp.ui.favorites

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.miguel.moviesapp.data.AppRepository
import com.miguel.moviesapp.data.Movie
import com.miguel.moviesapp.data.Serie
import com.miguel.moviesapp.ui.filters.MovieFilter
import com.miguel.moviesapp.ui.filters.SeriesFilter
import kotlinx.coroutines.launch

class FavoritesViewModel @ViewModelInject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private var currentMovieQuery = MutableLiveData(DEFAULT_MOVIE_TITLE_QUERY)
    private var currentSeriesQuery  =  MutableLiveData(DEFAULT_SERIES_TITLE_QUERY)


    fun deleteMovie(movie: Movie) = viewModelScope.launch {
        repository.deleteMovie(movie)
    }

    fun deleteSerie(serie: Serie) = viewModelScope.launch {
        repository.deleteSerie(serie)
    }


    val favoriteMovies = currentMovieQuery.switchMap { filter ->
        repository.searchFavoriteMovies(filter)
    }

    val favoriteSeries = currentSeriesQuery.switchMap { filter ->
        repository.searchFavoriteSeries(filter)
    }

    fun searchFavoriteMovies(filter: MovieFilter) {
        currentMovieQuery.value = filter
    }
    fun searchFavoriteSeries(filter: SeriesFilter) {
        currentSeriesQuery.value = filter
    }

    companion object {
        private val DEFAULT_MOVIE_TITLE_QUERY = MovieFilter(null, null, true,
            null, null)
        private val DEFAULT_SERIES_TITLE_QUERY = SeriesFilter(null, null,
            true, null)
    }
}