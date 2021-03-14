package com.miguel.moviesapp.ui.filters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.layout.Layout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.miguel.moviesapp.R
import com.miguel.moviesapp.databinding.FilterItemLayoutBinding
import com.miguel.moviesapp.databinding.MoviesFilterLayoutBinding

class FilterRecyclerAdapter(private val context: Context,
                            private val listener: MovieFilterInterface,
                            private val values: ArrayList<String>,
                            private var selectedPosition: Int,
                            private val typeOfList: Int
                            ) : RecyclerView.Adapter<FilterRecyclerAdapter.FilterViewHolder>() {

    inner class FilterViewHolder(private val binding: FilterItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(value: String, positionPassed: Int){
            binding.apply {
                filterValueTextView.text = value
                if(positionPassed == selectedPosition){
                    filterValueTextView.setTextColor(ContextCompat.getColor(context, R.color.dark_grey))
                    filterBackground.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow))
                }

                filterBackground.setOnClickListener {
                    when(typeOfList){
                        MovieFilterViewModel.LANGUAGE_FILTER_ADAPTER ->{
                            listener.onLanguageFilterChanged(values[layoutPosition])
                        }
                        MovieFilterViewModel.COUNTRY_FILTER_ADAPTER -> {
                            listener.onCountryFilterChanged(values[layoutPosition])
                        }
                        MovieFilterViewModel.YEAR_FILTER_ADAPTER -> {
                            listener.onYearFilterChanged(values[layoutPosition].toInt())
                        }
                    }
                }
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val binding = FilterItemLayoutBinding.inflate(LayoutInflater.from(parent.context),
        parent, false)
        return FilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.bind(values[position], position)
    }

    override fun getItemCount(): Int = values.size


}