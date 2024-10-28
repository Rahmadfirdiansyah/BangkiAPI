package com.dicoding.bangkitapi.ui.finished

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.bangkitapi.R
import com.dicoding.bangkitapi.data.response.ListEventsItem

class EventAdapter(
    private var eventList: List<ListEventsItem?>,
    private val onItemClick: (ListEventsItem?) -> Unit
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.img_item_photo)
        val itemName: TextView = itemView.findViewById(R.id.item_name)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
        val ownerName: TextView = itemView.findViewById(R.id.item_description)

        init {
            // Set the click listener for the itemView
            itemView.setOnClickListener {
                val eventItem = eventList[adapterPosition]
                onItemClick(eventItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_finished, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val eventItem = eventList[position]

        // Set event name
        holder.itemName.text = eventItem?.name
        holder.ownerName.text = eventItem?.ownerName

        // Load image using Glide or other image loading libraries
        if (eventItem?.imageLogo != null) {
            holder.progressBar.visibility = View.VISIBLE
            Glide.with(holder.itemView.context)
                .load(eventItem.imageLogo)
                .into(holder.imageView)
            holder.progressBar.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    // Update data method for updating list
    fun updateData(newEventList: List<ListEventsItem?>) {
        eventList = newEventList
        notifyDataSetChanged() // Notify the adapter to update UI
    }
}