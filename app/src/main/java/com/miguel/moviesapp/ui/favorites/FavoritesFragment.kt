package com.miguel.moviesapp.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.miguel.moviesapp.R
import com.miguel.moviesapp.databinding.FavoritesFragmentLayoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.favorites_fragment_layout) {


    private var _binding : FavoritesFragmentLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FavoritesFragmentLayoutBinding.bind(view)

        // Going to use the scope of this fragment for obtaining the data
        // Still have to make an adapter for this recyclerview
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}