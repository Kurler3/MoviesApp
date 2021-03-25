package com.miguel.moviesapp.ui.series

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.miguel.moviesapp.R
import com.miguel.moviesapp.data.Serie
import com.miguel.moviesapp.databinding.MovieSerieItemWithFilterLayoutBinding
import com.miguel.moviesapp.room.onMovieSeriesLongClicked
import com.miguel.moviesapp.ui.OnMovieSeriesClickListener

class SeriesWithFilterAdapter(private val onLongClickListener: onMovieSeriesLongClicked,
                              private val onClickListener: OnMovieSeriesClickListener) :
    PagingDataAdapter<Serie, SeriesWithFilterAdapter.SeriesWithFilterViewHolder>(SERIES_COMPARATOR) {

    inner class SeriesWithFilterViewHolder(private val binding: MovieSerieItemWithFilterLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(serie: Serie) {
            binding.apply {
                Glide.with(itemView)
                    .load(serie.posterURL)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(posterImage)

                titleTextView.text = serie.name

                imdbRateTextView.text = (serie.vote_average / 2).toString()

                releaseDate.text = "Release Date: ${serie.first_air_date}"

                // Genres missing
                genresTextView.text = "Genres:"

                itemView.setOnClickListener {
                    onClickListener.onSeriesClicked(getItem(layoutPosition))
                }

                itemView.setOnLongClickListener {
                    // Create an alert dialog
                    val builder = AlertDialog.Builder(it.context)
                        .setTitle("Add This Series To Favorites")
                        .setMessage("Are you sure you want to add this series to your favorite series list?")
                        .setPositiveButton("Confirm") { dialog, _ ->
                            // Notify the interface implementer
                            onLongClickListener.onSerieAddedToFavorites(getItem(layoutPosition))
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
        private val SERIES_COMPARATOR = object : DiffUtil.ItemCallback<Serie>(){
            override fun areItemsTheSame(oldItem: Serie, newItem: Serie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Serie, newItem: Serie): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: SeriesWithFilterViewHolder, position: Int) {
        var series = getItem(position)

        if(series!=null) holder.bind(series)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesWithFilterViewHolder {
        val binding = MovieSerieItemWithFilterLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SeriesWithFilterViewHolder(binding)
    }
}