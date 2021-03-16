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
            if(MovieFilterViewModel.convertToLanguagePosition(currentFilter.language)!=-1){
                smoothScrollToPosition(MovieFilterViewModel.convertToLanguagePosition(currentFilter.language))
            }
        }
        binding.regionFilterRecyclerView.apply {
            setHasFixedSize(true)
            adapter = regionRecyclerAdapter
            if(MovieFilterViewModel.convertToCountryPosition(currentFilter.country)!=-1) {
                smoothScrollToPosition(MovieFilterViewModel.convertToCountryPosition(currentFilter.country))
            }
        }
        binding.yearFilterRecyclerView.apply{
            setHasFixedSize(true)
            adapter = yearRecyclerAdapter
            if(MovieFilterViewModel.convertToYearPosition(currentFilter.year)!=-1) {
                smoothScrollToPosition(MovieFilterViewModel.convertToYearPosition(currentFilter.year))
            }
        }


        binding.adultFilterSwitch.setOnCheckedChangeListener { _, isChecked ->
            if(currentFilter!=null){
                currentFilter!!.includeAdult = isChecked

                viewModel.changeFilter(currentFilter!!)
            }
        }

        // Clear all filters at once and update the MovieFilter object in the viewmodel
        binding.filterClearButton.setOnClickListener {
            // Here have to update the currentFilter and re-assign the adapters for all recyclerviews

            currentFilter = MovieFilter(currentFilter.query, null, true, null, null)

            changeAdapter(MovieFilterViewModel.LANGUAGE_FILTER_ADAPTER, null)
            changeAdapter(MovieFilterViewModel.COUNTRY_FILTER_ADAPTER, null)
            changeAdapter(MovieFilterViewModel.YEAR_FILTER_ADAPTER, null)
        }

        // Go back to the MovieListFragment and send the new MovieFilter to it, which then is send to it's own viewmodel
        binding.filterSeeResultsButton.setOnClickListener {
            // Go back on navigation component and send the currentFilter to the list fragment, updating the currentFilter there and
            // obtaining search results accordingly
        }

        viewModel.movieFilter.observe(viewLifecycleOwner){
            currentFilter = it
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    /* Interface that connects fragment with the adapters */
    /* Whenever the filter is changed, a new adapter should be made and attached to the corresponding recycler view */

    override fun onLanguageFilterChanged(position: Int) {
        val newLanguage = MovieFilterViewModel.getLanguageFilterValue(position)

        // Update the filter
        currentFilter = MovieFilter(currentFilter.query, newLanguage, currentFilter.includeAdult,
        currentFilter.country, currentFilter.year)

        // Change the Adapter for this recycler view
        changeAdapter(MovieFilterViewModel.LANGUAGE_FILTER_ADAPTER, newLanguage)
    }
    
    // Do the same for these two
    override fun onCountryFilterChanged(position: Int) {
        val newCountry = MovieFilterViewModel.getCountryFilterValue(position)

        currentFilter = MovieFilter(currentFilter.query, currentFilter.language, currentFilter.includeAdult,
                newCountry, currentFilter.year)

        changeAdapter(MovieFilterViewModel.COUNTRY_FILTER_ADAPTER, newCountry)
    }

    override fun onYearFilterChanged(position: Int) {
        val newYear = MovieFilterViewModel.getYearFilterValue(position)

        currentFilter = MovieFilter(currentFilter.query, currentFilter.language, currentFilter.includeAdult,
                currentFilter.country, newYear.toInt())

        changeAdapter(MovieFilterViewModel.YEAR_FILTER_ADAPTER, newYear)
    }

    private fun changeAdapter(typeAdapter: Int, newValue: String?){
        when(typeAdapter){
            MovieFilterViewModel.LANGUAGE_FILTER_ADAPTER ->{
                // Have to create a new adapter for the language recycler view
                val languageRecyclerAdapter = view?.context?.let {
                    FilterRecyclerAdapter(it,this,
                            viewModel.LANGUAGE_FILTERS, MovieFilterViewModel.convertToLanguagePosition(newValue), MovieFilterViewModel.LANGUAGE_FILTER_ADAPTER)
                }
                binding.languageFilterRecyclerView.apply {
                    adapter = languageRecyclerAdapter
                    if(MovieFilterViewModel.convertToLanguagePosition(currentFilter.language)!=-1){
                        smoothScrollToPosition(MovieFilterViewModel.convertToLanguagePosition(currentFilter.language))
                    }
                }
            }
            MovieFilterViewModel.COUNTRY_FILTER_ADAPTER -> {
                // Have to create a new adapter for the region recycler view
                val regionRecyclerAdapter = view?.context?.let {
                    FilterRecyclerAdapter(it,this,
                            viewModel.COUNTRY_FILTERS, MovieFilterViewModel.convertToCountryPosition(newValue), MovieFilterViewModel.COUNTRY_FILTER_ADAPTER)
                }
                binding.regionFilterRecyclerView.apply {
                    adapter = regionRecyclerAdapter
                    if(MovieFilterViewModel.convertToCountryPosition(currentFilter.country)!=-1) {
                        smoothScrollToPosition(MovieFilterViewModel.convertToCountryPosition(currentFilter.country))
                    }
                }
            }
            MovieFilterViewModel.YEAR_FILTER_ADAPTER -> {
                // Have to create a new adapter for the year recycler view
                val yearRecyclerAdapter = view?.context?.let {
                    FilterRecyclerAdapter(it,this,
                            viewModel.YEAR_FILTERS, MovieFilterViewModel.convertToYearPosition(newValue?.toInt()), MovieFilterViewModel.YEAR_FILTER_ADAPTER)
                }
                binding.yearFilterRecyclerView.apply {
                    adapter = yearRecyclerAdapter
                    if(MovieFilterViewModel.convertToYearPosition(currentFilter.year)!=-1) {
                        smoothScrollToPosition(MovieFilterViewModel.convertToYearPosition(currentFilter.year))
                    }
                }
            }
        }
    }
}