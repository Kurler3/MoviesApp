package com.miguel.moviesapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Serie(
        val id: Int,
        val name: String,
        val poster_path: String?,
        val overview: String,
        val first_air_date: String,
        val genre_ids: Array<Int>,
        val original_language: String,
        val popularity: Float,
        val vote_average: Float,
        val origin_country: Array<String>
) : Parcelable {
    val posterURL get() = "https://image.tmdb.org/t/p/original${poster_path}"
}