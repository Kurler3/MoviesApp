package com.miguel.moviesapp.ui.movies

import android.content.DialogInterface
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.miguel.moviesapp.R
import com.miguel.moviesapp.data.Movie
import com.miguel.moviesapp.databinding.MovieItemLayoutBinding
import com.miguel.moviesapp.room.onFavorableItemsLongClicked
import kotlin.coroutines.coroutineContext

class MoviesAdapter(private val listener: onFavorableItemsLongClicked) : PagingDataAdapter<Movie, MoviesAdapter.MovieViewHolder>(
    MOVIE_COMPARATOR
) {


    inner class MovieViewHolder(private val binding: MovieItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){

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

            itemView.setOnLongClickListener {
                // Create an alert dialog
                val builder = AlertDialog.Builder(it.context)
                    .setTitle("Add This Movie To Favorites")
                    .setMessage("Are you sure you want to add this movie to your favorite movies list?")
                    .setPositiveButton("Confirm") { dialog, _ ->
                        // Notify the interface implementer
                        listener.onMovieAddedToFavorites(getItem(layoutPosition))
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }

                builder.create().show()

                true
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

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)

        if(movie!=null) holder.bind(movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            MovieItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MovieViewHolder(binding)
    }
}