package com.miguel.moviesapp.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.miguel.moviesapp.ui.filters.MovieFilter
import com.miguel.moviesapp.api.movie.AppAPI
import com.miguel.moviesapp.api.movie.MovieApiResponse
import com.miguel.moviesapp.api.movie.SeriesApiResponse
import com.miguel.moviesapp.data.Movie
import com.miguel.moviesapp.data.Serie
import com.miguel.moviesapp.ui.filters.SeriesFilter
import retrofit2.HttpException
import java.io.IOException


class SeriesPagingSource(
        private val appApi: AppAPI,
        private val filter: SeriesFilter
) : PagingSource<Int, Serie>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Serie> {
        val position = params.key ?: PagingSourceConstants.STARTING_PAGE_INDEX

        return try {
            val response = searchFilter(filter, position)

            val series = response.results

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

    override fun getRefreshKey(state: PagingState<Int, Serie>): Int? {
        TODO("Not yet implemented")
    }

    private suspend fun searchFilter(filter : SeriesFilter, position : Int) : SeriesApiResponse {
        if(filter.query==null){
            return appApi.searchSeriesPopular(AppAPI.CLIENT_ID, position, filter.language)
        }
        return appApi.searchSeries(AppAPI.CLIENT_ID,
                    filter.query, position, filter.language, filter.includeAdult, filter.firstAiredYear)

    }
}