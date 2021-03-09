package com.miguel.moviesapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie (
    val id: Int,
    val title: String,
    val poster_path: String?,
    val overview: String,
    val release_date: String,
    val genre_ids: Array<Int>,
    val original_language: String,
    val popularity: Float,
    val vote_average: Float,
    val has_video: Boolean,
    val region: String,
    val include_adult: Boolean
    ) : Parcelable {
        val posterURL get() = "https://image.tmdb.org/t/p/original${poster_path}"
    }