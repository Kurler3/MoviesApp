package com.miguel.moviesapp.api

 class MovieFilter(
    val query: String?,
    val language: String?,
    val includeAdult: Boolean?,
    val country: String?,
    val year: Int?
)