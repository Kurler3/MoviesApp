package com.miguel.moviesapp.ui.filters

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.miguel.moviesapp.R
import com.miguel.moviesapp.api.MovieFilter
import com.miguel.moviesapp.databinding.MoviesFilterLayoutBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.movies_filter_layout.*

@AndroidEntryPoint
class MovieFilterFragment : Fragment(R.layout.movies_filter_layout) {

    private val viewModel by viewModels<MovieFilterViewModel>()

    private var _binding : MoviesFilterLayoutBinding? = null

    private val binding get() = _binding!!

    private var currentFilter : MovieFilter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = MoviesFilterLayoutBinding.bind(view)

        // Create the rest of the adapters for the other recycler views

        val languageRecyclerAdapter = FilterRecyclerAdapter(viewModel.LANGUAGE_FILTERS)

        language_filter_recycler_view.apply {

        }
        region_filter_recycler_view.apply {

        }
        year_filter_recycler_view.apply{

        }


        adult_filter_switch.setOnCheckedChangeListener { buttonView, isChecked ->

        }

        // Clear all filters at once and update the MovieFilter object in the viewmodel
        clear_filters_button.setOnClickListener {

        }

        // Go back to the MovieListFragment and send the new MovieFilter to it, which then is send to it's own viewmodel
        results_filter_button.setOnClickListener {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}