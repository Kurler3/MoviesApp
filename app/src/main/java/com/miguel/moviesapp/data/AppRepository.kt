package com.miguel.moviesapp.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.miguel.moviesapp.ui.filters.MovieFilter
import com.miguel.moviesapp.api.movie.AppAPI
import com.miguel.moviesapp.data.pagingsource.MoviesPagingSource
import com.miguel.moviesapp.data.pagingsource.SeriesPagingSource
import com.miguel.moviesapp.room.dao.MoviesDao
import com.miguel.moviesapp.room.dao.SeriesDao
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

    fun searchFavoriteMovies(filter: MovieFilter) : LiveData<List<Movie>> {
        if(filter.query!=null) return moviesDao.findByTitle(filter.query)
        return moviesDao.getAll()
    }

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


    fun searchFavoriteSeries(filter: SeriesFilter) : LiveData<List<Serie>> {
        if(filter.query!=null) return seriesDao.findByTitle(filter.query)
        return seriesDao.getAll()
    }

    suspend fun insertMovie(movie: Movie) = moviesDao.insert(movie)
    suspend fun deleteMovie(movie: Movie) = moviesDao.delete(movie)

    suspend fun insertSerie(serie: Serie) = seriesDao.insert(serie)
    suspend fun deleteSerie(serie: Serie) = seriesDao.delete(serie)

}