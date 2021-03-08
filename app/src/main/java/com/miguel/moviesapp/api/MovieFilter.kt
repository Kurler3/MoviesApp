package com.miguel.moviesapp.api

data class MovieFilter(
        val type: String,
        val query: String,
        val filterValue: String
){
    companion object {
        const val NO_FILTER = "none"
        const val TITLE_FILTER = "title"
        const val LANGUAGE_FILTER = "language"
        const val ADULT_FILTER = "adult"
        const val REGION_FILTER = "region"
        const val YEAR_FILTER = "year"
        const val RELEASE_YEAR_FILTER = "release_year"
    }
}