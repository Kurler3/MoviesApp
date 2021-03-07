package com.miguel.moviesapp.api

class MovieQuery(
    val query:String,
    val language : String?,
    val includeAdult: Boolean?,
    val region: String?,
    val year: Int?,
    val primaryReleaseYear: Int?
)