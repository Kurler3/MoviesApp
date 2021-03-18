package com.miguel.moviesapp.ui.filters

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.miguel.moviesapp.R
import com.miguel.moviesapp.databinding.SeriesFilterLayoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeriesFilterFragment : Fragment(R.layout.series_filter_layout), FilterInterface {
    companion object {
        val CURRENT_SERIES_FILTER = "currentSeriesFilter"
    }
    private val args : SeriesFilterFragmentArgs by navArgs()

    private val viewModel by viewModels<FilterViewModel>()

    private var _binding : SeriesFilterLayoutBinding? = null

    private val binding get() = _binding!!

    private lateinit var currentFilter : SeriesFilter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = SeriesFilterLayoutBinding.bind(view)

        currentFilter = args.currentSeriesFilter

        // Create the rest of the adapters for the other recycler views

        val languageRecyclerAdapter = FilterRecyclerAdapter(view.context,this,
                viewModel.LANGUAGE_FILTERS, FilterViewModel.convertToLanguagePosition(currentFilter.language), FilterViewModel.LANGUAGE_FILTER_ADAPTER)


        val yearRecyclerAdapter = FilterRecyclerAdapter(view.context,this,viewModel.YEAR_FILTERS,
                FilterViewModel.convertToYearPosition(currentFilter.firstAiredYear), FilterViewModel.YEAR_FILTER_ADAPTER)

        // Initialize the switch
        binding.adultFilterSwitch.isChecked = currentFilter.includeAdult

        // Setting the Recycler Views up
        binding.languageFilterRecyclerView.apply {
            setHasFixedSize(true)
            adapter = languageRecyclerAdapter
            if(FilterViewModel.convertToLanguagePosition(currentFilter.language) !=-1){
                smoothScrollToPosition(FilterViewModel.convertToLanguagePosition(currentFilter.language))
            }
        }

        binding.yearFilterRecyclerView.apply{
            setHasFixedSize(true)
            adapter = yearRecyclerAdapter
            if(FilterViewModel.convertToYearPosition(currentFilter.firstAiredYear) !=-1) {
                smoothScrollToPosition(FilterViewModel.convertToYearPosition(currentFilter.firstAiredYear))
            }
        }


        binding.adultFilterSwitch.setOnCheckedChangeListener { _, isChecked ->
            currentFilter.includeAdult = isChecked
        }

        // Clear all filters at once and update the MovieFilter object in the viewmodel
        binding.filterClearButton.setOnClickListener {
            // Here have to update the currentFilter and re-assign the adapters for all recyclerviews

            currentFilter = SeriesFilter(currentFilter.query, null, true, null)

            changeAdapter(FilterViewModel.LANGUAGE_FILTER_ADAPTER, null)
            changeAdapter(FilterViewModel.YEAR_FILTER_ADAPTER, null)
        }

        // Go back to the MovieListFragment and send the new MovieFilter to it, which then is send to it's own viewmodel
        binding.filterSeeResultsButton.setOnClickListener {
            // Go back on navigation component and send the currentFilter to the list fragment, updating the currentFilter there and
            // obtaining search results accordingly
            findNavController().previousBackStackEntry?.savedStateHandle?.set(CURRENT_SERIES_FILTER, currentFilter)
            findNavController().popBackStack()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    /* Interface that connects fragment with the adapters */
    /* Whenever the filter is changed, a new adapter should be made and attached to the corresponding recycler view */

    override fun onLanguageFilterChanged(position: Int) {
        val newLanguage = FilterViewModel.getLanguageFilterValue(position)

        // Update the filter
        currentFilter = SeriesFilter(currentFilter.query, newLanguage, currentFilter.includeAdult,
        currentFilter.firstAiredYear)

        // Change the Adapter for this recycler view
        changeAdapter(FilterViewModel.LANGUAGE_FILTER_ADAPTER, newLanguage)
    }

    // There's no use for this in this filter fragment
    override fun onCountryFilterChanged(newCountry: Int) {
        TODO("Not yet implemented")
    }

    override fun onYearFilterChanged(position: Int) {
        val newYear = FilterViewModel.getYearFilterValue(position)

        currentFilter = SeriesFilter(currentFilter.query, currentFilter.language, currentFilter.includeAdult,
                newYear.toInt())

        changeAdapter(FilterViewModel.YEAR_FILTER_ADAPTER, newYear)
    }

    private fun changeAdapter(typeAdapter: Int, newValue: String?){
        when(typeAdapter){
            FilterViewModel.LANGUAGE_FILTER_ADAPTER ->{
                // Have to create a new adapter for the language recycler view
                val languageRecyclerAdapter = view?.context?.let {
                    FilterRecyclerAdapter(it,this,
                            viewModel.LANGUAGE_FILTERS, FilterViewModel.convertToLanguagePosition(newValue), FilterViewModel.LANGUAGE_FILTER_ADAPTER)
                }
                binding.languageFilterRecyclerView.apply {
                    adapter = languageRecyclerAdapter
                    if(FilterViewModel.convertToLanguagePosition(currentFilter.language) !=-1){
                        smoothScrollToPosition(FilterViewModel.convertToLanguagePosition(currentFilter.language))
                    }
                }
            }
            FilterViewModel.YEAR_FILTER_ADAPTER -> {
                // Have to create a new adapter for the year recycler view
                val yearRecyclerAdapter = view?.context?.let {
                    FilterRecyclerAdapter(it,this,
                            viewModel.YEAR_FILTERS, FilterViewModel.convertToYearPosition(newValue?.toInt()), FilterViewModel.YEAR_FILTER_ADAPTER)
                }
                binding.yearFilterRecyclerView.apply {
                    adapter = null
                    adapter = yearRecyclerAdapter
                    if(FilterViewModel.convertToYearPosition(currentFilter.firstAiredYear) !=-1) {
                        smoothScrollToPosition(FilterViewModel.convertToYearPosition(currentFilter.firstAiredYear))
                    }
                }
            }
        }
    }
}