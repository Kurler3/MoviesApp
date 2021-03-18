package com.miguel.moviesapp.ui.series

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.miguel.moviesapp.R
import com.miguel.moviesapp.data.Movie
import com.miguel.moviesapp.data.Serie
import com.miguel.moviesapp.databinding.MovieItemLayoutBinding
import com.miguel.moviesapp.databinding.SerieItemLayoutBinding

class SeriesAdapter : PagingDataAdapter<Serie, SeriesAdapter.SerieViewHolder>(
    SERIE_COMPARATOR
) {


    inner class SerieViewHolder(private val binding: SerieItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(serie : Serie){
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
    }

    companion object {
        private val SERIE_COMPARATOR = object : DiffUtil.ItemCallback<Serie>(){
            override fun areItemsTheSame(oldItem: Serie, newItem: Serie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Serie, newItem: Serie): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: SerieViewHolder, position: Int) {
        val serie = getItem(position)

        if(serie!=null) holder.bind(serie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SerieViewHolder {
        val binding =
            SerieItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SerieViewHolder(binding)
    }
}