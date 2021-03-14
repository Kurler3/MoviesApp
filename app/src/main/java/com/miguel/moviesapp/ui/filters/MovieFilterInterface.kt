package com.miguel.moviesapp.ui.filters

interface MovieFilterInterface {
    fun onLanguageFilterChanged(newLanguage: String)
    fun onCountryFilterChanged(newCountry: String)
    fun onYearFilterChanged(newYear : Int)
}