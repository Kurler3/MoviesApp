package com.miguel.moviesapp.room.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.miguel.moviesapp.data.Movie

@Dao
interface MoviesDao {

    // Using this one if there's no filters
    @Query("SELECT * FROM movies_table")
    fun getAll() : LiveData<List<Movie>>

    @Query("SELECT * FROM movies_table WHERE title LIKE :title")
    fun findByTitle(title: String) : LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)
}