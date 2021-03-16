package com.miguel.moviesapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.miguel.moviesapp.ui.filters.MovieFilter
import com.miguel.moviesapp.api.movie.MovieAPI
import com.miguel.moviesapp.data.pagingsource.MoviesPagingSource
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MoviesRepository @Inject constructor(private val movieApi: MovieAPI) {

    fun search(filter: MovieFilter) =
        Pager(
                config = PagingConfig(
                        pageSize = 20,
                        maxSize = 200,
                        enablePlaceholders = false
                ),
                pagingSourceFactory = { MoviesPagingSource(movieApi,
                        filter)
                }
        ).liveData

}