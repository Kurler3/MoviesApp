package com.miguel.moviesapp.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.miguel.moviesapp.data.Movie
import com.miguel.moviesapp.data.Serie
import com.miguel.moviesapp.room.Converters
import com.miguel.moviesapp.room.dao.MoviesDao
import com.miguel.moviesapp.room.dao.SeriesDao

@Database(
    entities = [Movie::class, Serie::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun getMoviesDao() : MoviesDao
    abstract fun getSeriesDao() : SeriesDao
}