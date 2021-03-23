package com.miguel.moviesapp.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miguel.moviesapp.api.movie.AppAPI
import com.miguel.moviesapp.api.movie.MoviesGenresApiResponse
import com.miguel.moviesapp.api.movie.SeriesGenresApiResponse
import com.miguel.moviesapp.data.AppRepository
import kotlinx.coroutines.launch

class SingleMovieSerieViewModel @ViewModelInject constructor(
    private val repository: AppRepository,
    private val appAPI: AppAPI
) : ViewModel(){

    fun getMovieGenresText(idArray : Array<Int>) : LiveData<String>{
        var genres : MoviesGenresApiResponse
        var result = MutableLiveData("")
        // the genres array
        viewModelScope.launch {
            genres = appAPI.searchMoviesGenres(AppAPI.CLIENT_ID)
            var genresNames = ""
            // We want to return it as a string
            genres.genres.forEach { genre ->
                if(genre.id in idArray){
                    genresNames += " ${genre.name},"
                }
            }
            genresNames = genresNames.dropLast(1)

            result.value = "Genre(s):$genresNames"
        }
        return result
    }

    fun getSeriesGenresText(idArray : Array<Int>) : LiveData<String>{
        var genres : SeriesGenresApiResponse
        var result = MutableLiveData("")
        // the genres array
        viewModelScope.launch {
            genres = appAPI.searchSeriesGenres(AppAPI.CLIENT_ID)
            var genresNames = ""
            // We want to return it as a string
            genres.genres.forEach { genre ->
                if(genre.id in idArray){
                    genresNames += " ${genre.name},"
                }
            }
            genresNames = genresNames.dropLast(1)

            result.value = "Genre(s):$genresNames"
        }
        return result
    }


    // Have some functions that will add/remove the movie/series from the database through
    // the single movie/series fragment
}