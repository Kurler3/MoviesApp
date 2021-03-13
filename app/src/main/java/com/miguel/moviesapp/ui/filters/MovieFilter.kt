package com.miguel.moviesapp.ui.filters

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieFilter(
    val query: String?,
    val language: String?,
    var includeAdult: Boolean,
    val country: String?,
    val year: Int?
) : Parcelable