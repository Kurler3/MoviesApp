package com.miguel.moviesapp.room.recycleradapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.miguel.moviesapp.R
import com.miguel.moviesapp.data.Movie
import com.miguel.moviesapp.data.Serie
import com.miguel.moviesapp.databinding.MovieItemLayoutBinding
import com.miguel.moviesapp.databinding.SerieItemLayoutBinding

class FavoriteSeriesAdapter : ListAdapter<Serie, FavoriteSeriesAdapter.FavoriteSeriesViewHolder>(SERIE_COMPARATOR) {

    private var series : List<Serie> = listOf()

    class FavoriteSeriesViewHolder(private val binding: SerieItemLayoutBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(serie: Serie){
            binding.apply {
                Glide.with(itemView)
                    .load(serie.posterURL)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageViewSerie)

                textViewSerie.text = serie.name
            }
        }

        // Have to set an on long item click listener for removing items
        }

    companion object {
        private val SERIE_COMPARATOR = object : DiffUtil.ItemCallback<Serie>(){
            override fun areItemsTheSame(oldItem: Serie, newItem: Serie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Serie, newItem: Serie): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: FavoriteSeriesViewHolder, position: Int) {
        val serie = series[position]

        if(serie!=null) holder.bind(serie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteSeriesViewHolder {
        val binding =
            SerieItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FavoriteSeriesViewHolder(binding)
    }

    fun setSeries(series: List<Serie>) {
        this.series = series
        notifyDataSetChanged()
    }
}