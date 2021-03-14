package com.miguel.moviesapp.ui.filters

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import javax.inject.Singleton


class MovieFilterViewModel @ViewModelInject constructor(): ViewModel(){

    // Need to get the MovieFilter that the MoviesListFragment was using when this was launched and assign it here
    val movieFilter = MutableLiveData(DEFAULT_MOVIE_FILTER)



    fun changeFilter(filter : MovieFilter){
        movieFilter.value = filter
    }

    val LANGUAGE_FILTERS : ArrayList<String> = getLanguages()
    val COUNTRY_FILTERS = getCountries()
    val YEAR_FILTERS = getYears()

    companion object {

        val LANGUAGE_FILTER_ADAPTER = 1
        val COUNTRY_FILTER_ADAPTER = 2
        val YEAR_FILTER_ADAPTER = 3

        private val DEFAULT_MOVIE_FILTER = MovieFilter(null, null,
                true, null, null)

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
    }
}