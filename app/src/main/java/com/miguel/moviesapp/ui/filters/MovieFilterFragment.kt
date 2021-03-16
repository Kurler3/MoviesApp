package com.miguel.moviesapp.ui.filters

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.miguel.moviesapp.R
import com.miguel.moviesapp.databinding.MoviesFilterLayoutBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.movies_filter_layout.*
import java.util.logging.Filter

@AndroidEntryPoint
class MovieFilterFragment : Fragment(R.layout.movies_filter_layout), MovieFilterInterface {

    private val args : MovieFilterFragmentArgs by navArgs()

    private val viewModel by viewModels<MovieFilterViewModel>()

    private var _binding : MoviesFilterLayoutBinding? = null

    private val binding get() = _binding!!

    private lateinit var currentFilter : MovieFilter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = MoviesFilterLayoutBinding.bind(view)

        currentFilter = args.currentMovieFilter

        // Create the rest of the adapters for the other recycler views

        val languageRecyclerAdapter = FilterRecyclerAdapter(view.context,this,
                viewModel.LANGUAGE_FILTERS, MovieFilterViewModel.convertToLanguagePosition(currentFilter.language), MovieFilterViewModel.LANGUAGE_FILTER_ADAPTER)

        val regionRecyclerAdapter = FilterRecyclerAdapter(view.context,this,viewModel.COUNTRY_FILTERS,
                MovieFilterViewModel.convertToCountryPosition(currentFilter.country), MovieFilterViewModel.COUNTRY_FILTER_ADAPTER)

        val yearRecyclerAdapter = FilterRecyclerAdapter(view.context,this,viewModel.YEAR_FILTERS,
                MovieFilterViewModel.convertToYearPosition(currentFilter.year), MovieFilterViewModel.YEAR_FILTER_ADAPTER)

        // Initialize the switch
        binding.adultFilterSwitch.isChecked = currentFilter.includeAdult

        // Setting the Recycler Views up
        binding.languageFilterRecyclerView.apply {
            setHasFixedSize(true)
            adapter = languageRecyclerAdapter
        }
        binding.regionFilterRecyclerView.apply {
            setHasFixedSize(true)
            adapter = regionRecyclerAdapter
        }
        binding.yearFilterRecyclerView.apply{
            setHasFixedSize(true)
            adapter = yearRecyclerAdapter
        }


        binding.adultFilterSwitch.setOnCheckedChangeListener { _, isChecked ->
            if(currentFilter!=null){
                currentFilter!!.includeAdult = isChecked

                viewModel.changeFilter(currentFilter!!)
            }
        }

        // Clear all filters at once and update the MovieFilter object in the viewmodel
        binding.filterClearButton.setOnClickListener {

        }

        // Go back to the MovieListFragment and send the new MovieFilter to it, which then is send to it's own viewmodel
        binding.filterSeeResultsButton.setOnClickListener {

        }

        viewModel.movieFilter.observe(viewLifecycleOwner){
            currentFilter = it
        }
    }
    /*
    private fun convertToLanguagePosition() : Int {
        var position = -1
        if(currentFilter.language!=null){
            when(currentFilter.language){
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

    private fun convertToCountryPosition() : Int {
        var position = -1
        if(currentFilter.country!=null){
            when(currentFilter.country){
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

    private fun convertToYearPosition() : Int {
        var position = -1
        if(currentFilter.year!=null){
            position = MovieFilterViewModel.getYears().indexOf(currentFilter.year)
        }
        return position
    }
    */

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    /* Interface that connects fragment with the adapters */
    /* Whenever the filter is changed, a new adapter should be made and attached to the corresponding recycler view */

    override fun onLanguageFilterChanged(position: Int) {
        val newLanguage = MovieFilterViewModel.getLanguageFilterValue(position)

        currentFilter = MovieFilter(currentFilter.query, newLanguage, currentFilter.includeAdult,
        currentFilter.country, currentFilter.year)

        // Have to create a new adapter for the language recycler view
        val languageRecyclerAdapter = view?.context?.let {
            FilterRecyclerAdapter(it,this,
                viewModel.LANGUAGE_FILTERS, MovieFilterViewModel.convertToLanguagePosition(newLanguage), MovieFilterViewModel.LANGUAGE_FILTER_ADAPTER)
        }
        binding.languageFilterRecyclerView.adapter = languageRecyclerAdapter
    }
    
    // Do the same for these two
    override fun onCountryFilterChanged(position: Int) {
        val newCountry = MovieFilterViewModel.getCountryFilterValue(position)

        currentFilter = MovieFilter(currentFilter.query, currentFilter.language, currentFilter.includeAdult,
                newCountry, currentFilter.year)

        // Have to create a new adapter for the language recycler view
        val regionRecyclerAdapter = view?.context?.let {
            FilterRecyclerAdapter(it,this,
                    viewModel.COUNTRY_FILTERS, MovieFilterViewModel.convertToCountryPosition(newCountry), MovieFilterViewModel.COUNTRY_FILTER_ADAPTER)
        }
        binding.regionFilterRecyclerView.adapter = regionRecyclerAdapter
    }

    override fun onYearFilterChanged(position: Int) {
        val newYear = MovieFilterViewModel.getYearFilterValue(position)

        currentFilter = MovieFilter(currentFilter.query, currentFilter.language, currentFilter.includeAdult,
                currentFilter.country, newYear)

        // Have to create a new adapter for the language recycler view
        val yearRecyclerAdapter = view?.context?.let {
            FilterRecyclerAdapter(it,this,
                    viewModel.YEAR_FILTERS, MovieFilterViewModel.convertToYearPosition(newYear), MovieFilterViewModel.YEAR_FILTER_ADAPTER)
        }
        binding.yearFilterRecyclerView.adapter = yearRecyclerAdapter
    }
}