package com.miguel.moviesapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.miguel.moviesapp.databinding.MoviesLoadStateFooterLayoutBinding

class AppLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<AppLoadStateAdapter.AppLoadStateViewHolder>() {

    inner class AppLoadStateViewHolder(
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

    override fun onBindViewHolder(holder: AppLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): AppLoadStateViewHolder {
        val binding = MoviesLoadStateFooterLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AppLoadStateViewHolder(binding)
    }


}