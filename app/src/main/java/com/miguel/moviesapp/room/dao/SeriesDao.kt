package com.miguel.moviesapp.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.miguel.moviesapp.data.Movie
import com.miguel.moviesapp.data.Serie

@Dao
interface SeriesDao {

    @Query("SELECT * FROM series_table")
    fun getAll() : LiveData<List<Serie>>

    @Query("SELECT * FROM series_table WHERE title like :title")
    fun findByTitle(title: String) : LiveData<List<Serie>>

    @Insert
    suspend fun insert(serie: Serie)

    @Delete
    suspend fun delete(serie: Serie)
}