package com.miguel.moviesapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie (
    val id: Int,
    val title: String,
    val poster_path: String?,
    val overview: String,
    val releaseDate: String,
    val genreIds: Array<Int>,
    val originalLanguage: String,
    val popularity: Float,
    val vote_average: Float,
    val hasVideo: Boolean,
    val isAdult: Boolean
    ) : Parcelable {
        val posterURL get() = "https://image.tmdb.org/t/p/original${poster_path}"
    }