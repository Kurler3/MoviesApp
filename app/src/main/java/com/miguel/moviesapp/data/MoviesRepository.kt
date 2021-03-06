package com.miguel.moviesapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.miguel.moviesapp.api.MovieAPI
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MoviesRepository @Inject constructor(private val movieApi: MovieAPI) {

    fun getSearchedMovies(query : String,
                          language : String?,
                          includeAdult: Boolean?,
                          region: String?,
                          year: Int?,
                          primaryReleaseYear: Int?){
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 200,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviesPagingSource(movieApi,
                query,
                language,
                includeAdult,
                region,
                year,
                primaryReleaseYear)
            }
        ).liveData
    }
}