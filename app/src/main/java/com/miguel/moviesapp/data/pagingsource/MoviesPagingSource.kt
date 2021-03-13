package com.miguel.moviesapp.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.miguel.moviesapp.ui.filters.MovieFilter
import com.miguel.moviesapp.api.MovieAPI
import com.miguel.moviesapp.api.MovieApiResponse
import com.miguel.moviesapp.data.Movie
import retrofit2.HttpException
import java.io.IOException


class MoviesPagingSource(
    private val movieApi:MovieAPI,
    private val filter: MovieFilter
) : PagingSource<Int, Movie>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: PagingSourceConstants.STARTING_PAGE_INDEX

        return try {
            val response = searchFilter(filter, position)

            val movies = response.results

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

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        TODO("Not yet implemented")
    }

    private suspend fun searchFilter(filter : MovieFilter, position : Int) : MovieApiResponse {
        if(filter.query==null){
            return movieApi.searchPopular(MovieAPI.CLIENT_ID, position, filter.language, filter.country)
        }
        return movieApi.searchMovies(MovieAPI.CLIENT_ID,
                    filter.query, position, filter.language, filter.includeAdult, filter.country, filter.year)

    }
}