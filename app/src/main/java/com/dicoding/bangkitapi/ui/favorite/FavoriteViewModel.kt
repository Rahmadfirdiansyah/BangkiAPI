package com.dicoding.bangkitapi.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.bangkitapi.database.FavoriteEvent
import com.dicoding.bangkitapi.repository.FavoriteEventRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteEventRepository: FavoriteEventRepository = FavoriteEventRepository(application)

    // Mengambil semua acara favorit
    fun getAllFavoriteEvents(): LiveData<List<FavoriteEvent>> = mFavoriteEventRepository.getAllFavoriteEvents()

    // Mengambil acara favorit berdasarkan ID
    fun getFavoriteEventById(id: String): LiveData<FavoriteEvent> {
        return mFavoriteEventRepository.getFavoriteEventById(id)
    }

    // Menambahkan acara favorit
    fun insertFavoriteEvent(favoriteEvent: FavoriteEvent) {
        viewModelScope.launch {
            mFavoriteEventRepository.insert(favoriteEvent)
        }
    }

    // Menghapus acara favorit
    fun deleteFavoriteEvent(favoriteEvent: FavoriteEvent) {
        viewModelScope.launch {
            mFavoriteEventRepository.delete(favoriteEvent)
        }
    }
}
