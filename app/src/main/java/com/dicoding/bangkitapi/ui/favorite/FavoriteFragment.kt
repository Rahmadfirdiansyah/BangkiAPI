package com.dicoding.bangkitapi.ui.favorite

import FavoriteEventAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bangkitapi.R
import com.dicoding.bangkitapi.data.response.ListEventsItem

class FavoriteFragment : Fragment() {
    private lateinit var favoriteEventViewModel: FavoriteViewModel
    private lateinit var favoriteEventAdapter: FavoriteEventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewFavorite)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Inisialisasi adapter dengan onClick
        favoriteEventAdapter = FavoriteEventAdapter { event ->
            val intent = Intent(requireContext(), FavoriteDetailActivity::class.java).apply {
                putExtra(FavoriteDetailActivity.EXTRA_EVENT_ID, event.id)
            }
            startActivity(intent) // Pindah ke FavoriteDetailActivity
        }
        recyclerView.adapter = favoriteEventAdapter

        val application = requireActivity().application
        val factory = ViewModelFactory.getInstance(application)
        favoriteEventViewModel = ViewModelProvider(this, factory).get(FavoriteViewModel::class.java)

        favoriteEventViewModel.getAllFavoriteEvents().observe(viewLifecycleOwner) { favoriteEvents ->
            favoriteEventAdapter.submitList(favoriteEvents) // Gunakan submitList
        }

        return view
    }
}



