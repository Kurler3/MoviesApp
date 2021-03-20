package com.miguel.moviesapp.room.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.miguel.moviesapp.data.Movie
import com.miguel.moviesapp.data.pagingsource.PagingSourceConstants
import com.miguel.moviesapp.room.dao.MoviesDao
import com.miguel.moviesapp.ui.filters.MovieFilter
import retrofit2.HttpException
import java.io.IOException

class RoomMoviePagingSource(
    private val moviesDao: MoviesDao,
    private val movieFilter: MovieFilter
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: PagingSourceConstants.STARTING_PAGE_INDEX

        return try {

            val movies = searchFilter(movieFilter)

            LoadResult.Page(
                data = movies,
                prevKey = if (position == PagingSourceConstants.STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if(movies.isEmpty()) null else position + 1
            )

            //IO exception will be thrown when there is no internet connection
        }catch (exception: IOException){
            LoadResult.Error(exception)

            //HTTP exception will be thrown if there is no authorization or if there's no data
        }catch (exception: HttpException){
            LoadResult.Error(exception)
        }
    }

    suspend fun searchFilter(filter: MovieFilter) : List<Movie>{
        if(filter.query!=null) {
            return moviesDao.findByTitle(filter.query)
        }
        return moviesDao.getAll()
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        TODO("Not yet implemented")
    }
}