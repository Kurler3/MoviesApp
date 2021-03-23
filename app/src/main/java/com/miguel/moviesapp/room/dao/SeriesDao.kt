package com.miguel.moviesapp.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.miguel.moviesapp.data.Movie
import com.miguel.moviesapp.data.Serie

@Dao
interface SeriesDao {

    @Query("SELECT * FROM series_table")
    fun getAll() : LiveData<List<Serie>>

    @Query("SELECT * FROM series_table WHERE title like :title")
    fun findByTitle(title: String) : LiveData<List<Serie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(serie: Serie)

    @Delete
    suspend fun delete(serie: Serie)
}