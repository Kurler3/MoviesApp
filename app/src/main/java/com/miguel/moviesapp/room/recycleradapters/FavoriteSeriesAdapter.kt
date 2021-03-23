package com.miguel.moviesapp.room.recycleradapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.miguel.moviesapp.R
import com.miguel.moviesapp.data.Serie
import com.miguel.moviesapp.databinding.SerieItemLayoutBinding
import com.miguel.moviesapp.ui.OnMovieSeriesClickListener
import com.miguel.moviesapp.room.onMovieSeriesLongClicked

class FavoriteSeriesAdapter(
    private val onLongClickListener: onMovieSeriesLongClicked,
    private val onClickListener: OnMovieSeriesClickListener
    ) : ListAdapter<Serie, FavoriteSeriesAdapter.FavoriteSeriesViewHolder>(SERIE_COMPARATOR) {

    private var series : List<Serie> = listOf()

    inner class FavoriteSeriesViewHolder(private val binding: SerieItemLayoutBinding)
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

            itemView.setOnClickListener {
                onClickListener.onSeriesClicked(series[layoutPosition])
            }

            itemView.setOnLongClickListener {
                // Create an alert dialog
                val builder = AlertDialog.Builder(it.context)
                    .setTitle("Remove This Series From Favorites")
                    .setMessage("Are you sure you want to remove this series from your favorite series list?")
                    .setPositiveButton("Confirm") { _, _ ->
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

    override fun getItemCount(): Int {
        return series.size
    }
}