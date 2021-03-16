package com.miguel.moviesapp.ui.filters

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel


class MovieFilterViewModel @ViewModelInject constructor(): ViewModel(){


    val LANGUAGE_FILTERS : ArrayList<String> = getLanguages()
    val COUNTRY_FILTERS = getCountries()
    val YEAR_FILTERS = getYears()

    companion object {

        val LANGUAGE_FILTER_ADAPTER = 1
        val COUNTRY_FILTER_ADAPTER = 2
        val YEAR_FILTER_ADAPTER = 3

        fun getLanguageFilterValue(position : Int) : String {
            return when (position) {
                0 -> "en-US"
                1 -> "de-CH"
                2 -> "fr-FR"
                3 -> "pt-PT"
                4 -> "zh-CN"
                5 -> "ja-JP"
                else -> "-1"
            }
        }
        fun getCountryFilterValue(position : Int) : String {
            return when (position) {
                0 -> "US"
                1 -> "CA"
                2 -> "DE"
                3 -> "FR"
                4 -> "PT"
                5 -> "CN"
                6 -> "JP"
                else -> "-1"
            }
        }
        fun getYearFilterValue(position: Int) : String = getYears()[position]
        
        fun getYears() : ArrayList<String>{
            var array : ArrayList<String> = ArrayList()
            for (i in 0..9){
                array.add("200${i}")
            }
            for (i in 10..21){
                array.add("20${i}")
            }
            return array
        }
        fun getLanguages() : ArrayList<String> {
            var array : ArrayList<String> = ArrayList()
            val langArray = arrayOf("English", "German", "French", "Portuguese", "Chinese", "Japanese")
            for (lang in langArray) {
                array.add(lang)
            }
            return array
        }
        fun getCountries() : ArrayList<String> {
            var array : ArrayList<String> = ArrayList()
            var countryArray = arrayOf("United States", "Canada", "Germany", "France", "Portugal", "China", "Japan")
            for(country in countryArray)  array.add(country)
            return array
        }


        fun convertToLanguagePosition(language: String?) : Int {
            var position = -1
            if(language!=null){
                when(language){
                    "en-US" -> position = 0
                    "de-CH" -> position = 1
                    "fr-FR" -> position = 2
                    "pt-PT" -> position = 3
                    "zh-CN" -> position = 4
                    "ja-JP" -> position = 5
                }
            }
            return position
        }

        fun convertToCountryPosition(country: String?) : Int {
            var position = -1
            if(country!=null){
                when(country){
                    "US" -> position = 0
                    "CA" -> position = 1
                    "DE" -> position = 2
                    "FR" -> position = 3
                    "PT" -> position = 4
                    "CN" -> position = 5
                    "JP" -> position = 6
                }
            }
            return position
        }

        fun convertToYearPosition(year: Int?) : Int {
            var position = -1
            if(year!=null){
                position = getYears().indexOf(year.toString())
            }
            return position
        }
    }
}