package com.miguel.moviesapp.ui.filters

interface FilterInterface {
    fun onLanguageFilterChanged(newLanguage: Int)
    fun onCountryFilterChanged(newCountry: Int)
    fun onYearFilterChanged(newYear : Int)
}