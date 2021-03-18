package com.miguel.moviesapp.ui.series

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.miguel.moviesapp.data.AppRepository
import com.miguel.moviesapp.ui.filters.SeriesFilter

class SeriesViewModel @ViewModelInject constructor(
    private val repository: AppRepository)
    : ViewModel(){

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    // Using MutableLiveData switchMap means that photos will change whenever the value of currentQuery changes. Can be thought
    // of like a listener for mutable live data
    val series = currentQuery.switchMap { filter ->
        // Have to cache the live data otherwise the app will crash when rotating
        // because cant load from the same page data twice
        repository.searchSeries(filter).cachedIn(viewModelScope)
    }

    fun searchSeries(filter: SeriesFilter){
        currentQuery.value = filter
    }

    companion object {
        private val DEFAULT_QUERY = SeriesFilter(null, null,
            true, null)
    }
}