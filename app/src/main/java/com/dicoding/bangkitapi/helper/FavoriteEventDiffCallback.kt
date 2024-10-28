package com.dicoding.bangkitapi.helper

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.bangkitapi.database.FavoriteEvent

class FavoriteEventDiffCallback(
    private val oldFavoriteList: List<FavoriteEvent>,
    private val newFavoriteList: List<FavoriteEvent>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldFavoriteList.size

    override fun getNewListSize(): Int = newFavoriteList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // Bandingkan berdasarkan ID dari event favorit
        return oldFavoriteList[oldItemPosition].id == newFavoriteList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // Bandingkan name dan mediaCover dari event favorit
        val oldEvent = oldFavoriteList[oldItemPosition]
        val newEvent = newFavoriteList[newItemPosition]
        return oldEvent.name == newEvent.name && oldEvent.mediaCover == newEvent.mediaCover
    }
}
