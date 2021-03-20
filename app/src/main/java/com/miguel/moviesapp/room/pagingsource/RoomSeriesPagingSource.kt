package com.miguel.moviesapp.room.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.miguel.moviesapp.data.Movie
import com.miguel.moviesapp.data.Serie
import com.miguel.moviesapp.data.pagingsource.PagingSourceConstants
import com.miguel.moviesapp.room.dao.SeriesDao
import com.miguel.moviesapp.ui.filters.MovieFilter
import com.miguel.moviesapp.ui.filters.SeriesFilter
import retrofit2.HttpException
import java.io.IOException

class RoomSeriesPagingSource(
    private val seriesDao: SeriesDao,
    private val seriesFilter: SeriesFilter
) : PagingSource<Int, Serie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Serie> {
        val position = params.key ?: PagingSourceConstants.STARTING_PAGE_INDEX

        return try {

            val series = searchFilter(seriesFilter)

            LoadResult.Page(
                data = series,
                prevKey = if (position == PagingSourceConstants.STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if(series.isEmpty()) null else position + 1
            )

            //IO exception will be thrown when there is no internet connection
        }catch (exception: IOException){
            LoadResult.Error(exception)

            //HTTP exception will be thrown if there is no authorization or if there's no data
        }catch (exception: HttpException){
            LoadResult.Error(exception)
        }
    }

    suspend fun searchFilter(filter: SeriesFilter) : List<Serie>{
        if(filter.query!=null) {
            return seriesDao.findByTitle(filter.query)
        }
        return seriesDao.getAll()
    }

    override fun getRefreshKey(state: PagingState<Int, Serie>): Int? {
        TODO("Not yet implemented")
    }
}