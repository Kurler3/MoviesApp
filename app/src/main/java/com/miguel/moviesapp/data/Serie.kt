package com.miguel.moviesapp.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "series_table")
@Parcelize
data class Serie(
        @PrimaryKey val id: Int,
        @ColumnInfo(name = "title") val name: String,
        @ColumnInfo(name = "poster_path") val poster_path: String?,
        @ColumnInfo(name = "overview") val overview: String,
        @ColumnInfo(name = "first_air_date") val first_air_date: String,
        @ColumnInfo(name = "genre_ids") val genre_ids: Array<Int>,
        @ColumnInfo(name = "original_language") val original_language: String,
        @ColumnInfo(name = "popularity") val popularity: Float,
        @ColumnInfo(name = "vote_average") val vote_average: Float,
        @ColumnInfo(name = "origin_country") val origin_country: Array<String>
) : Parcelable {
    val posterURL get() = "https://image.tmdb.org/t/p/original${poster_path}"
}