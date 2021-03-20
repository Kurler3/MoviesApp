package com.miguel.moviesapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.miguel.moviesapp.ui.filters.MovieFilter
import com.miguel.moviesapp.api.movie.AppAPI
import com.miguel.moviesapp.data.pagingsource.MoviesPagingSource
import com.miguel.moviesapp.data.pagingsource.SeriesPagingSource
import com.miguel.moviesapp.room.dao.MoviesDao
import com.miguel.moviesapp.room.dao.SeriesDao
import com.miguel.moviesapp.room.pagingsource.RoomMoviePagingSource
import com.miguel.moviesapp.room.pagingsource.RoomSeriesPagingSource
import com.miguel.moviesapp.ui.filters.SeriesFilter
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppRepository @Inject constructor(
    private val appApi: AppAPI,
    private val moviesDao: MoviesDao,
    private val seriesDao: SeriesDao
) {

    fun searchMovies(filter: MovieFilter) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 200,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviesPagingSource(
                    appApi,
                    filter
                )
            }
        ).liveData

    fun searchFavoriteMovies(filter: MovieFilter) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 200,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                RoomMoviePagingSource(
                    moviesDao,
                    filter
                )
            }
        ).liveData

    fun searchSeries(filter: SeriesFilter) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 200,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SeriesPagingSource(
                    appApi,
                    filter
                )
            }
        ).liveData

    fun searchFavoriteSeries(filter: SeriesFilter) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 200,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                RoomSeriesPagingSource(
                    seriesDao,
                    filter
                )
            }
        ).liveData

    suspend fun insertMovie(movie: Movie) = moviesDao.insert(movie)
    suspend fun deleteMovie(movie: Movie) = moviesDao.delete(movie)

    suspend fun insertSerie(serie: Serie) = seriesDao.insert(serie)
    suspend fun deleteSerie(serie: Serie) = seriesDao.delete(serie)

}