package com.dicoding.bangkitapi.ui.finished

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.bangkitapi.databinding.FragmentFinishedBinding


class FinishedFragment : Fragment() {

    private lateinit var finishedViewModel: FinishedViewModel
    private lateinit var adapter: EventAdapter
    private lateinit var binding: FragmentFinishedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using ViewBinding
        binding = FragmentFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        finishedViewModel = ViewModelProvider(this).get(FinishedViewModel::class.java)

        // Setup RecyclerView
        adapter = EventAdapter(emptyList()) { selectedEvent ->
            // Handle item click and navigate to UpcomingDetailActivity
            val intent = Intent(requireActivity(), FinishedDetailActivity::class.java)
            intent.putExtra("EVENT_DATA", selectedEvent)
            startActivity(intent)
        }
        binding.recyclerViewFinished.adapter = adapter
        binding.recyclerViewFinished.layoutManager = LinearLayoutManager(requireContext())

        // Tampilkan ProgressBar sebelum data dimuat
        binding.progressBar.visibility = View.VISIBLE

        // Observe LiveData from ViewModel for events
        finishedViewModel.eventData.observe(viewLifecycleOwner) { eventResponse ->
            eventResponse?.let {
                val events = it.listEvents ?: emptyList() // Assumes listEvents is a property of EventResponse
                if (events.isNotEmpty()) {
                    adapter.updateData(events)
                } else {
                    Toast.makeText(requireContext(), "No events found", Toast.LENGTH_SHORT).show()
                }
            }
            // Sembunyikan ProgressBar setelah data selesai dimuat
            binding.progressBar.visibility = View.GONE
        }

        // Fetch events when the fragment is created
        finishedViewModel.getEvents()
    }
}