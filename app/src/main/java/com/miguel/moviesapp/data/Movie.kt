package com.miguel.moviesapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie (
    val id: Int,
    val title: String,
    val posterPath: String?,
    val overview: String,
    val releaseDate: String,
    val genreIds: Array<String>,
    val originalLanguage: String,
    val popularity: Int,
    val vote_average: Int,
    val hasVideo: Boolean,
    val isAdult: Boolean
    ) : Parcelable