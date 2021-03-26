package com.miguel.moviesapp.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.miguel.moviesapp.R
import com.miguel.moviesapp.data.Movie
import com.miguel.moviesapp.data.MovieGenres
import com.miguel.moviesapp.databinding.MovieSerieItemWithFilterLayoutBinding
import com.miguel.moviesapp.room.onMovieSeriesLongClicked
import com.miguel.moviesapp.ui.OnMovieSeriesClickListener

class FavoriteMoviesWithFilterAdapter(private val onLongClickListener: onMovieSeriesLongClicked,
                                      private val onClickListener: OnMovieSeriesClickListener) :
    ListAdapter<Movie, FavoriteMoviesWithFilterAdapter.MoviesWithFilterViewHolder>(MOVIE_COMPARATOR) {

    private var movies: List<Movie> = listOf()

    inner class MoviesWithFilterViewHolder(private val binding: MovieSerieItemWithFilterLayoutBinding) : RecyclerView.ViewHolder(binding.root){
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


                itemView.setOnClickListener {
                    onClickListener.onMovieClicked(movies[layoutPosition])
                }

                itemView.setOnLongClickListener {
                    // Create an alert dialog
                    val builder = AlertDialog.Builder(it.context)
                        .setTitle("Remove This Movie From Favorites")
                        .setMessage("Are you sure you want to remove this movie from your favorite movies list?")
                        .setPositiveButton("Confirm") { dialog, _ ->
                            // Notify the interface implementer
                            onLongClickListener.onMovieRemovedFromFavorites(movies[layoutPosition])
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
        var movie = movies[position]

        if(movie!=null) holder.bind(movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesWithFilterViewHolder {
        val binding = MovieSerieItemWithFilterLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MoviesWithFilterViewHolder(binding)
    }
    override fun getItemCount(): Int = movies.size

    fun setMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }
}