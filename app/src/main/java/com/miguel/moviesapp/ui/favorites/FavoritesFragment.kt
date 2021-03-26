package com.miguel.moviesapp.ui.favorites

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.miguel.moviesapp.R
import com.miguel.moviesapp.data.Movie
import com.miguel.moviesapp.data.Serie
import com.miguel.moviesapp.databinding.FavoritesFragmentLayoutBinding
import com.miguel.moviesapp.room.onMovieSeriesLongClicked
import com.miguel.moviesapp.room.recycleradapters.FavoriteMoviesAdapter
import com.miguel.moviesapp.room.recycleradapters.FavoriteSeriesAdapter
import com.miguel.moviesapp.ui.AppLoadStateAdapter
import com.miguel.moviesapp.ui.OnMovieSeriesClickListener
import com.miguel.moviesapp.ui.filters.FilterFragment
import com.miguel.moviesapp.ui.filters.MovieFilter
import com.miguel.moviesapp.ui.filters.SeriesFilter
import com.miguel.moviesapp.ui.filters.SeriesFilterFragment
import com.miguel.moviesapp.ui.movies.MoviesWithFilterAdapter
import com.miguel.moviesapp.ui.series.SeriesWithFilterAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.full.memberProperties

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.favorites_fragment_layout), onMovieSeriesLongClicked,
OnMovieSeriesClickListener{

    private val viewModel : FavoritesViewModel by viewModels()

    private var _binding : FavoritesFragmentLayoutBinding? = null
    private val binding get() = _binding!!

    private var displayingMovies = true
    private var currentMovieFilter = MovieFilter(null, null, true, null, null)
    private var currentSeriesFilter = SeriesFilter(null, null, true, null)

    private val moviesAdapter = FavoriteMoviesAdapter(this, this)
    private val seriesAdapter = FavoriteSeriesAdapter(this, this)

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

            textViewEmptyFavorites.text = if (displayingMovies) {
                resources.getString(R.string.you_have_no_favorite_movies)
            }else resources.getString(R.string.you_have_no_favorite_series)

            favoritesTitleTextView.text = if (displayingMovies) {
                resources.getString(R.string.favorites_title_movies)
            }else resources.getString(R.string.favorites_title_series)
        }

        // Observing both favorite movies and series

        viewModel.favoriteMovies.observe(viewLifecycleOwner) {
            moviesAdapter.setMovies(it)
            // Checking if adapters are empty after the adapters were filled with data from Room
            checkIfAdapterEmpty()
        }

        viewModel.favoriteSeries.observe(viewLifecycleOwner) {
            seriesAdapter.setSeries(it)
            // Checking if adapters are empty after the adapters were filled with data from Room
            checkIfAdapterEmpty()
        }

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
                        currentMovieFilter = MovieFilter("%${query}%", currentMovieFilter.language, true, currentMovieFilter.country, currentMovieFilter.year)
                        viewModel.searchFavoriteMovies(currentMovieFilter)
                    }else {
                        currentSeriesFilter = SeriesFilter("%${query}%", currentSeriesFilter.language, true, currentSeriesFilter.firstAiredYear)
                        viewModel.searchFavoriteSeries(currentSeriesFilter)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && newText!="") {
                    if(displayingMovies){
                        currentMovieFilter = MovieFilter("%${newText}%",  currentMovieFilter.language, true, currentMovieFilter.country, currentMovieFilter.year)
                        viewModel.searchFavoriteMovies(currentMovieFilter)
                    }else {
                        currentSeriesFilter = SeriesFilter("%${newText}%", currentSeriesFilter.language, true, currentSeriesFilter.firstAiredYear)
                        viewModel.searchFavoriteSeries(currentSeriesFilter)
                    }
                }
                // This else ensures that when the text is erased from the search bar it will show the popular
                // Movies again
                else{
                    if(displayingMovies){
                        currentMovieFilter = MovieFilter(null, currentMovieFilter.language, true, currentMovieFilter.country, currentMovieFilter.year)
                        viewModel.searchFavoriteMovies(currentMovieFilter)
                    }else {
                        currentSeriesFilter = SeriesFilter(null, currentSeriesFilter.language, true, currentSeriesFilter.firstAiredYear)
                        viewModel.searchFavoriteSeries(currentSeriesFilter)
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

                binding.textViewEmptyFavorites.text = resources.getString(R.string.you_have_no_favorite_movies)

                checkIfAdapterEmpty()

                binding.favoritesTitleTextView.text = if (displayingMovies) {
                    resources.getString(R.string.favorites_title_movies)
                }else resources.getString(R.string.favorites_title_series)

            }
            R.id.change_to_series -> {
                displayingMovies = false

                binding.favoritesRecyclerView.adapter = seriesAdapter

                binding.textViewEmptyFavorites.text = resources.getString(R.string.you_have_no_favorite_series)

                checkIfAdapterEmpty()

                binding.favoritesTitleTextView.text = if (displayingMovies) {
                    resources.getString(R.string.favorites_title_movies)
                }else resources.getString(R.string.favorites_title_series)
            }
        }
        return true
    }

    private fun checkIfAdapterEmpty() {
        // Checking if adapters are empty after the adapters were filled with data from Room
        binding.apply {
            // If the recycler view is empty then show a text saying that
            textViewEmptyFavorites.isVisible = favoritesRecyclerView.adapter!!.itemCount <= 0
        }
    }

    override fun onMovieRemovedFromFavorites(movie: Movie?) {
        if(movie!=null) {
            viewModel.deleteMovie(movie)

            // Show some toast message
            val snackbar = Snackbar.make(requireView(), "Movie: ${movie!!.title} removed from Favorites",
                Snackbar.LENGTH_SHORT)
            snackbar.setAction("Ok") {
                snackbar.dismiss()
            }
            snackbar.show()
        }
    }

    override fun onSerieRemovedFromFavorites(serie: Serie?) {
        if(serie!=null) {
            viewModel.deleteSerie(serie)

            // Show some toast message
            val snackbar = Snackbar.make(requireView(), "Serie: ${serie!!.name} removed from Favorites",
                Snackbar.LENGTH_SHORT)
            snackbar.setAction("Ok") {
                snackbar.dismiss()
            }
            snackbar.show()
        }
    }

    // Also no need to implement this one
    override fun onSerieAddedToFavorites(serie: Serie?) {
        TODO("Not yet implemented")
    }
    // No need to implement this one
    override fun onMovieAddedToFavorites(movie: Movie?) {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onMovieClicked(movie: Movie?) {
        // Navigates to the fragment that displays the movie
        if(movie!=null){
            val action = FavoritesFragmentDirections.actionFavoritesFragmentToMovieSerieFragment(movie, null)
            findNavController().navigate(action)
        }
    }

    override fun onSeriesClicked(series: Serie?) {
        if(series!=null) {
            val action = FavoritesFragmentDirections.actionFavoritesFragmentToMovieSerieFragment(null, series)
            findNavController().navigate(action)
        }
    }
}