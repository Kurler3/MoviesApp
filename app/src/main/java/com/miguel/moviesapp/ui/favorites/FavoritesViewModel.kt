package com.miguel.moviesapp.ui.favorites

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.miguel.moviesapp.data.AppRepository
import com.miguel.moviesapp.data.Movie
import com.miguel.moviesapp.data.Serie
import com.miguel.moviesapp.ui.filters.MovieFilter
import com.miguel.moviesapp.ui.filters.SeriesFilter
import kotlinx.coroutines.launch

class FavoritesViewModel @ViewModelInject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private var currentMovieTitleQuery = MutableLiveData(DEFAULT_MOVIE_TITLE_QUERY)
    private var currentSeriesTitleQuery  =  MutableLiveData(DEFAULT_SERIES_TITLE_QUERY)

    /*
    fun insertMovie(movie: Movie) = viewModelScope.launch{
        repository.insertMovie(movie)
    }
    fun deleteMovie(movie: Movie) = viewModelScope.launch {
        repository.deleteMovie(movie)
    }

    fun insertSerie(serie: Serie) = viewModelScope.launch{
        repository.insertSerie(serie)
    }
    fun deleteSerie(serie: Serie) = viewModelScope.launch {
        repository.deleteSerie(serie)
    }

     */

    val favoriteMovies = currentMovieTitleQuery.switchMap { filter ->
        repository.searchFavoriteMovies(filter)
    }

    val favoriteSeries = currentSeriesTitleQuery.switchMap { filter ->
        repository.searchFavoriteSeries(filter)
    }

    fun changeCurrentMovieTitleQuery(filter: MovieFilter) {
        currentMovieTitleQuery.value = filter
    }
    fun changeCurrentSerieTitleQuery(filter: SeriesFilter) {
        currentSeriesTitleQuery.value = filter
    }

    companion object {
        private val DEFAULT_MOVIE_TITLE_QUERY = MovieFilter(null, null, true,
            null, null)
        private val DEFAULT_SERIES_TITLE_QUERY = SeriesFilter(null, null,
            true, null)
    }
}