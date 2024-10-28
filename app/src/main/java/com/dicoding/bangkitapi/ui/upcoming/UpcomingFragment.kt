package com.dicoding.bangkitapi.ui.upcoming

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.bangkitapi.databinding.FragmentUpcomingBinding

class UpcomingFragment : Fragment() {

    private lateinit var upcomingViewModel: UpcomingViewModel
    private lateinit var adapter: EventAdapterActive
    private lateinit var binding: FragmentUpcomingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using ViewBinding
        binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        upcomingViewModel = ViewModelProvider(this).get(UpcomingViewModel::class.java)

        // Setup RecyclerView
        adapter = EventAdapterActive(emptyList()) { selectedEvent ->
            // Handle item click and navigate to UpcomingDetailActivity
            val intent = Intent(requireActivity(), UpcomingDetailActivity::class.java)
            intent.putExtra("EVENT_DATA", selectedEvent)
            startActivity(intent)
        }
        binding.recyclerViewUpcoming.adapter = adapter
        binding.recyclerViewUpcoming.layoutManager = LinearLayoutManager(requireContext())

        // Tampilkan ProgressBar
        binding.progressBar.visibility = View.VISIBLE

        // Observe LiveData from ViewModel for events
        upcomingViewModel.eventData.observe(viewLifecycleOwner) { eventResponse ->
            eventResponse?.let {
                val events = it.listEvents ?: emptyList() // Assumes listEvents is a property of EventResponse
                if (events.isNotEmpty()) {
                    adapter.updateData(events) // Update RecyclerView data
                } else {
                    // Handle case when events list is empty
                    Toast.makeText(requireContext(), "No events found", Toast.LENGTH_SHORT).show()
                }
            }
            // Sembunyikan ProgressBar setelah data selesai dimuat
            binding.progressBar.visibility = View.GONE
        }

        // Fetch events when the fragment is created
        upcomingViewModel.getEventsActive()
    }
}