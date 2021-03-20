package com.miguel.moviesapp.room.recycleradapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.miguel.moviesapp.R
import com.miguel.moviesapp.data.Movie
import com.miguel.moviesapp.databinding.MovieItemLayoutBinding

class FavoriteMoviesAdapter :
    ListAdapter<Movie,FavoriteMoviesAdapter.FavoriteMoviesViewHolder>(MOVIE_COMPARATOR) {


    private var movies: List<Movie> = listOf()

    class FavoriteMoviesViewHolder(private val binding: MovieItemLayoutBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie : Movie){
            binding.apply {
                Glide.with(itemView)
                    .load(movie.posterURL)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageViewMovie)

                textViewMovie.text = movie.title
            }
        }

        // Have to set an on long item click listener for removing items
        }

    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Movie>(){
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: FavoriteMoviesViewHolder, position: Int) {
        val movie = movies[position]


        if(movie!=null) holder.bind(movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMoviesViewHolder {
        val binding =
            MovieItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FavoriteMoviesViewHolder(binding)
    }

    override fun getItemCount(): Int = movies.size

    fun setMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }
}