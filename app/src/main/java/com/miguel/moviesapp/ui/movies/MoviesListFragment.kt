package com.miguel.moviesapp.ui.movies

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.miguel.moviesapp.R
import com.miguel.moviesapp.ui.filters.MovieFilter
import com.miguel.moviesapp.databinding.MoviesListLayoutBinding
import com.miguel.moviesapp.ui.AppLoadStateAdapter
import com.miguel.moviesapp.ui.filters.FilterFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesListFragment : Fragment(R.layout.movies_list_layout) {

    private val moviesViewModel by viewModels<MoviesViewModel>()

    private var _binding : MoviesListLayoutBinding? = null

    private val binding get() = _binding!!

    private var currentFilter = MovieFilter(null, "en-US",
            true, null, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = MoviesListLayoutBinding.bind(view)

        val movieAdapter = MoviesAdapter()

        binding.apply {
            moviesRecyclerView.apply {
                setHasFixedSize(true)
                adapter = movieAdapter.withLoadStateHeaderAndFooter(
                    header = AppLoadStateAdapter{movieAdapter.retry()},
                    footer = AppLoadStateAdapter{movieAdapter.retry()}
                )
            }
        }

        moviesViewModel.movies.observe(viewLifecycleOwner) {
            movieAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        movieAdapter.addLoadStateListener { loadState ->
            binding.apply {
                // Check if its still loading or not
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                // Load state is finished and everything went well so the recycler view should be visible
                moviesRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                // If something went wrong (example: no internet connection)
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error

                // Check if its not loading and there is no error at the same time == No results
                if(loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    movieAdapter.itemCount < 1){
                    moviesRecyclerView.isVisible = false
                    textViewEmpty.isVisible = true
                }else {
                    textViewEmpty.isVisible = false
                }
            }
        }

        // Receiving changes for when the filter is changed in the MovieFilterFragment.kt
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<MovieFilter>(FilterFragment.CURRENT_MOVIE_FILTER)?.observe(
                viewLifecycleOwner) { newFilter ->
            // Update the current filter
            currentFilter = newFilter
            moviesViewModel.searchMovies(currentFilter)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.toolbar_menu_layout, menu)

            // Get the search view
        val searchItem = menu.findItem(R.id.search_menu_item)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                // This is temporary until I figure out how to use a current MovieFilter object instead
                if(query!=null){
                    currentFilter = MovieFilter(query=query, currentFilter.language, currentFilter.includeAdult, currentFilter.country, currentFilter.year)
                    moviesViewModel.searchMovies(currentFilter)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && newText!="") {
                    //val filter = MovieFilter(MovieFilter.TITLE_FILTER, newText, "")
                    currentFilter = MovieFilter(query = newText, currentFilter.language, currentFilter.includeAdult, currentFilter.country, currentFilter.year)
                    moviesViewModel.searchMovies(currentFilter)
                }
                // This else insures that when the text is erased from the search bar it will show the popular
                // Movies again
                else{
                    currentFilter = MovieFilter(null,  currentFilter.language, currentFilter.includeAdult, currentFilter.country, currentFilter.year)
                    moviesViewModel.searchMovies(currentFilter)
                }

               return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.filter_menu_item -> {
                // Should launch the MovieFilterFragment.kt through navigation component, returning some kind of filter
                val action = MoviesListFragmentDirections.actionMoviesListFragmentToMovieFilterFragment(currentFilter)
                findNavController().navigate(action)
            }
        }
        return true
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}