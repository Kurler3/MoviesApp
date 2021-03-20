package com.miguel.moviesapp.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.res.stringResource
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.miguel.moviesapp.R
import com.miguel.moviesapp.databinding.FavoritesLoadStateFooterLayoutBinding
import com.miguel.moviesapp.databinding.MoviesLoadStateFooterLayoutBinding

class FavoritesLoadStateAdapter(private val displayingMovies: Boolean) :
    LoadStateAdapter<FavoritesLoadStateAdapter.FavoritesLoadStateViewHolder>() {

    inner class FavoritesLoadStateViewHolder(
        private val binding: FavoritesLoadStateFooterLayoutBinding
        ) :
        RecyclerView.ViewHolder(binding.root){

            fun bind(loadState: LoadState){
                binding.apply {
                    loadStateProgressBar.isVisible = loadState is LoadState.Loading

                    textViewError.text = if(displayingMovies) stringResource(R.string.you_have_no_favorite_movies) else
                        stringResource(R.string.you_have_no_favorite_series)

                    textViewError.isVisible = loadState !is LoadState.Loading
                }
            }
    }

    override fun onBindViewHolder(holder: FavoritesLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): FavoritesLoadStateViewHolder {
        val binding = FavoritesLoadStateFooterLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoritesLoadStateViewHolder(binding)
    }


}