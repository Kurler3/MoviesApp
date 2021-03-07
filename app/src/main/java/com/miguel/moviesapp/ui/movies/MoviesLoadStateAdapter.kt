package com.miguel.moviesapp.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.miguel.moviesapp.databinding.MoviesLoadStateFooterLayoutBinding

class MoviesLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<MoviesLoadStateAdapter.MoviesLoadStateViewHolder>() {

    inner class MoviesLoadStateViewHolder(
        private val binding: MoviesLoadStateFooterLayoutBinding
        ) :
        RecyclerView.ViewHolder(binding.root){

            init {
                binding.buttonRetry.setOnClickListener {
                    retry.invoke()
                }
            }
            fun bind(loadState: LoadState){
                binding.apply {
                    loadStateProgressBar.isVisible = loadState is LoadState.Loading
                    textViewError.isVisible = loadState !is LoadState.Loading
                    buttonRetry.isVisible = loadState !is LoadState.Loading
                }
            }
    }

    override fun onBindViewHolder(holder: MoviesLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): MoviesLoadStateViewHolder {
        val binding = MoviesLoadStateFooterLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MoviesLoadStateViewHolder(binding)
    }


}