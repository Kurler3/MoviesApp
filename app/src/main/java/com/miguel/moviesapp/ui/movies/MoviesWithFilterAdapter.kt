package com.miguel.moviesapp.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.miguel.moviesapp.R
import com.miguel.moviesapp.data.Movie
import com.miguel.moviesapp.databinding.MovieItemWithFilterLayoutBinding
import com.miguel.moviesapp.room.onMovieSeriesLongClicked
import com.miguel.moviesapp.ui.OnMovieSeriesClickListener

class MoviesWithFilterAdapter(private val onLongClickListener: onMovieSeriesLongClicked,
private val onClickListener: OnMovieSeriesClickListener) :
    PagingDataAdapter<Movie, MoviesWithFilterAdapter.MoviesWithFilterViewHolder>(MOVIE_COMPARATOR) {

    inner class MoviesWithFilterViewHolder(private val binding: MovieItemWithFilterLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(movie: Movie) {
            binding.apply {
                Glide.with(itemView)
                    .load(movie.posterURL)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(posterImage)

                titleTextView.text = movie.title

                imdbRateTextView.text = (movie.vote_average / 2).toString()

                releaseDate.text = "Release Date: ${movie.release_date}"

                // Genres missing
                genresTextView.text = "Genres:"

                itemView.setOnClickListener {
                    onClickListener.onMovieClicked(getItem(layoutPosition))
                }

                itemView.setOnLongClickListener {
                    // Create an alert dialog
                    val builder = AlertDialog.Builder(it.context)
                        .setTitle("Add This Movie To Favorites")
                        .setMessage("Are you sure you want to add this movie to your favorite movies list?")
                        .setPositiveButton("Confirm") { dialog, _ ->
                            // Notify the interface implementer
                            onLongClickListener.onMovieAddedToFavorites(getItem(layoutPosition))
                        }
                        .setNegativeButton("Cancel") { dialog, _ ->
                            dialog.dismiss()
                        }

                    builder.create().show()

                    true
                }
            }
        }
    }

    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Movie>(){
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: MoviesWithFilterViewHolder, position: Int) {
        var movie = getItem(position)

        if(movie!=null) holder.bind(movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesWithFilterViewHolder {
        val binding = MovieItemWithFilterLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MoviesWithFilterViewHolder(binding)
    }
}