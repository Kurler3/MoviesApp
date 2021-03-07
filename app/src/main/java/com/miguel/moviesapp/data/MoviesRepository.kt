package com.miguel.moviesapp.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.miguel.moviesapp.api.MovieAPI
import com.miguel.moviesapp.data.pagingsource.MoviesPagingSourcePopular
import com.miguel.moviesapp.data.pagingsource.MoviesPagingSourceTitle
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MoviesRepository @Inject constructor(private val movieApi: MovieAPI) {

    fun getPopularMovies() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 200,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviesPagingSourcePopular(movieApi)
            }
        ).liveData

    fun getSearchedMovies(query : String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 200,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviesPagingSourceTitle(movieApi,
                query)
            }
        ).liveData


}