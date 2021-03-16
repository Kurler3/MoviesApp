package com.miguel.moviesapp.ui.filters

interface MovieFilterInterface {
    fun onLanguageFilterChanged(newLanguage: Int)
    fun onCountryFilterChanged(newCountry: Int)
    fun onYearFilterChanged(newYear : Int)
}