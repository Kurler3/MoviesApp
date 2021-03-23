package com.miguel.moviesapp.ui.movies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.miguel.moviesapp.R
import com.miguel.moviesapp.data.Movie
import com.miguel.moviesapp.data.Serie
import com.miguel.moviesapp.databinding.MovieSerieLayoutBinding
import com.miguel.moviesapp.ui.SingleMovieSerieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieSerieFragment : Fragment(R.layout.movie_serie_layout){

    private val viewmodel : SingleMovieSerieViewModel by viewModels()

    private var _binding : MovieSerieLayoutBinding? = null
    private val binding get() = _binding!!

    private val args : MovieSerieFragmentArgs by navArgs()

    private lateinit var movie: Movie
    private lateinit var series: Serie

    private var isMovie = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = MovieSerieLayoutBinding.bind(view)

        if(args.movie!=null){
            isMovie = true

            movie = args.movie!!

            binding.apply {
                Glide.with(view.context)
                    .load(movie.posterURL)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(posterImage)

                titleTextView.text = movie.title

                releaseDateTextView.text = "Release Date: ${movie.release_date}"
                ratingBar.rating = movie.vote_average / 2

                descriptionTextView.text = movie.overview
            }
        }else{
            series = args.series!!

            binding.apply {
                Glide.with(view.context)
                    .load(series.posterURL)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(posterImage)

                titleTextView.text = series.name

                releaseDateTextView.text = "Release Date: ${series.first_air_date}"
                ratingBar.rating = series.vote_average / 2

                descriptionTextView.text = series.overview
            }
        }


        if(isMovie){
            viewmodel.getMovieGenresText(movie.genre_ids).observe(viewLifecycleOwner) {
                binding.genresTextView.text = it
            }
        }else {
            viewmodel.getSeriesGenresText(series.genre_ids).observe(viewLifecycleOwner) {
                binding.genresTextView.text = it
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}