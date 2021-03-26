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
import com.miguel.moviesapp.data.Serie
import com.miguel.moviesapp.databinding.MovieSerieItemWithFilterLayoutBinding
import com.miguel.moviesapp.room.onMovieSeriesLongClicked
import com.miguel.moviesapp.ui.OnMovieSeriesClickListener

class FavoriteSeriesWithFilterAdapter(private val onLongClickListener: onMovieSeriesLongClicked,
                                      private val onClickListener: OnMovieSeriesClickListener) :
    ListAdapter<Serie, FavoriteSeriesWithFilterAdapter.SeriesWithFilterViewHolder>(SERIES_COMPARATOR) {

    private var series: List<Serie> = listOf()

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


                itemView.setOnClickListener {
                    onClickListener.onSeriesClicked(series[layoutPosition])
                }

                itemView.setOnLongClickListener {
                    // Create an alert dialog
                    val builder = AlertDialog.Builder(it.context)
                        .setTitle("Remove This Series From Favorites")
                        .setMessage("Are you sure you want to remove this series from your favorite series list?")
                        .setPositiveButton("Confirm") { dialog, _ ->
                            // Notify the interface implementer
                            onLongClickListener.onSerieRemovedFromFavorites(series[layoutPosition])
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
        var serie = series[position]

        if(serie!=null) holder.bind(serie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesWithFilterViewHolder {
        val binding = MovieSerieItemWithFilterLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SeriesWithFilterViewHolder(binding)
    }
    override fun getItemCount(): Int = series.size

    fun setSeries(series: List<Serie>) {
        this.series = series
        notifyDataSetChanged()
    }
}