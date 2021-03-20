package com.miguel.moviesapp.ui.favorites

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.compose.ui.res.stringResource
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.miguel.moviesapp.R
import com.miguel.moviesapp.databinding.FavoritesFragmentLayoutBinding
import com.miguel.moviesapp.room.FavoritesLoadStateAdapter
import com.miguel.moviesapp.room.recycleradapters.FavoriteMoviesAdapter
import com.miguel.moviesapp.room.recycleradapters.FavoriteSeriesAdapter
import com.miguel.moviesapp.ui.filters.MovieFilter
import com.miguel.moviesapp.ui.filters.SeriesFilter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.favorites_fragment_layout) {

    private val viewModel : FavoritesViewModel by viewModels()

    private var _binding : FavoritesFragmentLayoutBinding? = null
    private val binding get() = _binding!!

    private var displayingMovies = false
    private var currentMovieFilter = MovieFilter(null, null, true, null, null)
    private var currentSeriesFilter = SeriesFilter(null, null, true, null)

    private val moviesAdapter = FavoriteMoviesAdapter()
    private val seriesAdapter = FavoriteSeriesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FavoritesFragmentLayoutBinding.bind(view)

        // Going to use the scope of this fragment for obtaining the data
        // Still have to make an adapter for this recyclerview
        binding.apply {
            favoritesRecyclerView.apply {

                // Check initially if it should display movies or series
                adapter = if(displayingMovies){
                    moviesAdapter
                } else{
                    seriesAdapter
                }
            }
        }

        // Observing both favorite movies and series

        viewModel.favoriteMovies.observe(viewLifecycleOwner) {
            moviesAdapter.setMovies(it)
        }

        viewModel.favoriteSeries.observe(viewLifecycleOwner) {
            seriesAdapter.setSeries(it)
        }

        /*
        // Adding a loading state listener to both of the adapters
        moviesAdapter.addLoadStateListener { loadState ->
            binding.apply {
                // Check if its still loading or not
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                // Load state is finished and everything went well so the recycler view should be visible
                favoritesRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                // If something went wrong (example: no internet connection)
                textViewError.isVisible = loadState.source.refresh is LoadState.Error

                // Check if its not loading and there is no error at the same time == No results
                if(loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    moviesAdapter.itemCount < 1){
                    favoritesRecyclerView.isVisible = false
                    textViewEmptyFavorites.isVisible = true
                }else {
                    textViewEmptyFavorites.isVisible = false
                }
            }
        }

        seriesAdapter.addLoadStateListener { loadState ->
            binding.apply {
                // Check if its still loading or not
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                // Load state is finished and everything went well so the recycler view should be visible
                favoritesRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                // If something went wrong (example: no internet connection)
                textViewError.isVisible = loadState.source.refresh is LoadState.Error

                // Check if its not loading and there is no error at the same time == No results
                if(loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    seriesAdapter.itemCount < 1){
                    favoritesRecyclerView.isVisible = false
                    textViewEmptyFavorites.isVisible = true
                }else {
                    textViewEmptyFavorites.isVisible = false
                }
            }
        }
        */
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.favorites_options_menu, menu)

        // Get the search view
        val searchItem = menu.findItem(R.id.search_menu_item)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                // This is temporary until I figure out how to use a current MovieFilter object instead
                if(query!=null){
                    if(displayingMovies){
                        currentMovieFilter = MovieFilter("%${query}%", null, true, null, null)
                        viewModel.changeCurrentMovieTitleQuery(currentMovieFilter)
                    }else {
                        currentSeriesFilter = SeriesFilter("%${query}%", null, true, null)
                        viewModel.changeCurrentSerieTitleQuery(currentSeriesFilter)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && newText!="") {
                    if(displayingMovies){
                        currentMovieFilter = MovieFilter("%${newText}%", null, true, null, null)
                        viewModel.changeCurrentMovieTitleQuery(currentMovieFilter)
                    }else {
                        currentSeriesFilter = SeriesFilter("%${newText}%", null, true, null)
                        viewModel.changeCurrentSerieTitleQuery(currentSeriesFilter)
                    }
                }
                // This else ensures that when the text is erased from the search bar it will show the popular
                // Movies again
                else{
                    if(displayingMovies){
                        currentMovieFilter = MovieFilter(null, null, true, null, null)
                        viewModel.changeCurrentMovieTitleQuery(currentMovieFilter)
                    }else {
                        currentSeriesFilter = SeriesFilter(null, null, true, null)
                        viewModel.changeCurrentSerieTitleQuery(currentSeriesFilter)
                    }
                }

                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.change_to_movies -> {
                displayingMovies = true

                // Changing the recycler views adapters
                binding.favoritesRecyclerView.adapter = moviesAdapter
                    /*.withLoadStateHeader(
                    header = FavoritesLoadStateAdapter(displayingMovies)
                  )
                    */
                binding.textViewEmptyFavorites.text = resources.getString(R.string.you_have_no_favorite_movies)

            }
            R.id.change_to_series -> {
                displayingMovies = false

                binding.favoritesRecyclerView.adapter = seriesAdapter
                    /*.withLoadStateHeader(
                    header = FavoritesLoadStateAdapter(displayingMovies)
                )*/

                binding.textViewEmptyFavorites.text = resources.getString(R.string.you_have_no_favorite_series)
            }
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}