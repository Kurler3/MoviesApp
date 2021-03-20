package com.miguel.moviesapp.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.miguel.moviesapp.data.Movie

@Dao
interface MoviesDao {

    // Using this one if there's no filters
    @Query("SELECT * FROM movies_table")
    suspend fun getAll() : List<Movie>

    @Query("SELECT * FROM movies_table WHERE title LIKE :title")
    suspend fun findByTitle(title: String) : List<Movie>

    @Insert
    suspend fun insert(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)
}