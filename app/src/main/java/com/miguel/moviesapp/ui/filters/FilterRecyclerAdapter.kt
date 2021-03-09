package com.miguel.moviesapp.ui.filters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.layout.Layout
import androidx.recyclerview.widget.RecyclerView
import com.miguel.moviesapp.databinding.FilterItemLayoutBinding
import com.miguel.moviesapp.databinding.MoviesFilterLayoutBinding

class FilterRecyclerAdapter(private val values: Array<String>) : RecyclerView.Adapter<FilterRecyclerAdapter.FilterViewHolder>() {

    class FilterViewHolder(private val binding: FilterItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(value: String){
            binding.apply {
                filterValueTextView.text = value
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val binding = FilterItemLayoutBinding.inflate(LayoutInflater.from(parent.context),
        parent, false)
        return FilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.bind(values[position])
    }

    override fun getItemCount(): Int = values.size
}