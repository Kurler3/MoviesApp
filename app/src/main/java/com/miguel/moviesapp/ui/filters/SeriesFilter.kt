package com.miguel.moviesapp.ui.filters

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SeriesFilter(
        val query: String?,
        val language: String?,
        var includeAdult: Boolean,
        val firstAiredYear: Int?
): Parcelable