package com.miguel.moviesapp.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "movies_table")
@Parcelize
data class Movie (
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "poster") val poster_path: String?,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "release_date") val release_date: String,
    @ColumnInfo(name = "genre_ids") val genre_ids: Array<Int>,
    @ColumnInfo(name = "original_language") val original_language: String,
    @ColumnInfo(name = "popularity") val popularity: Float,
    @ColumnInfo(name = "vote_average") val vote_average: Float,
    val has_video: Boolean,
    @ColumnInfo(name = "region") val region: String,
    @ColumnInfo(name = "include_adult") val include_adult: Boolean
    ) : Parcelable {
    val posterURL get() = "https://image.tmdb.org/t/p/original${poster_path}"
    }