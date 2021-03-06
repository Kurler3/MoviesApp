package com.miguel.moviesapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.miguel.moviesapp.api.MovieAPI
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class MoviesPagingSource(
    private val movieApi:MovieAPI,
    private val query: String,
    private val language : String?,
    private val includeAdult: Boolean?,
    private val region: String?,
    private val year: Int?,
    private val primaryReleaseYear: Int?
) : PagingSource<Int, Movie>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = movieApi.searchMovies( MovieAPI.CLIENT_ID,
                query,
                position,
                language,
                includeAdult,
                region,
                year,
                primaryReleaseYear)

            val movies = response.results

            LoadResult.Page(
                data = movies,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
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
}