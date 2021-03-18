package com.miguel.moviesapp.ui.favorites

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.miguel.moviesapp.data.AppRepository
import com.miguel.moviesapp.data.Movie
import com.miguel.moviesapp.data.Serie

class FavoritesViewModel @ViewModelInject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private var currentMovieTitleQuery : String = DEFAULT_MOVIE_TITLE_QUERY
    private var currentSeriesTitleQuery : String = DEFAULT_SERIES_TITLE_QUERY


    suspend fun getAllFavoriteMovies() : List<Movie> = repository.allFavoriteMovies()
    suspend fun getFavoriteMoviesByTitle() : List<Movie> = repository.favoriteMoviesByTitle(currentMovieTitleQuery)
    suspend fun insertMovies(vararg movies: Movie) = repository.insertMovies(*movies)
    suspend fun deleteMovie(movie: Movie) = repository.deleteMovie(movie)

    suspend fun getAllFavoriteSeries() : List<Serie> = repository.allFavoriteSeries()
    suspend fun getFavoriteSeriesByTitle() : List<Movie> = repository.favoriteMoviesByTitle(currentSeriesTitleQuery)
    suspend fun insertSeries(vararg series: Serie) = repository.insertSeries(*series)
    suspend fun deleteMovie(serie: Serie) = repository.deleteSerie(serie)

    fun changeCurrentMovieTitleQuery(title: String) {
        currentMovieTitleQuery = title
    }
    fun changeCurrentSerieTitleQuery(title: String) {
        currentSeriesTitleQuery = title
    }

    companion object {
        private val DEFAULT_MOVIE_TITLE_QUERY = ""
        private val DEFAULT_SERIES_TITLE_QUERY = ""
    }
}