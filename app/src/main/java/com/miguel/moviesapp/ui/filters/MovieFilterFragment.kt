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
class MovieFilterFragment : Fragment(R.layout.movies_filter_layout) {

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

        val languageRecyclerAdapter = FilterRecyclerAdapter(viewModel.LANGUAGE_FILTERS)
        val regionRecyclerAdapter = FilterRecyclerAdapter(viewModel.COUNTRY_FILTERS)
        val yearRecyclerAdapter = FilterRecyclerAdapter(viewModel.YEAR_FILTERS)

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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}