package com.dicoding.bangkitapi.ui.favorite.insert

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.bangkitapi.database.FavoriteEvent
import com.dicoding.bangkitapi.repository.FavoriteEventRepository
import kotlinx.coroutines.launch

class FavoriteEventAddUpdateViewModel(application: Application) : AndroidViewModel(application) {
    private val mFavoriteEventRepository: FavoriteEventRepository = FavoriteEventRepository(application)

    fun insert(favoriteEvent: FavoriteEvent) {
        viewModelScope.launch {
            try {
                mFavoriteEventRepository.insert(favoriteEvent)
            } catch (e: Exception) {
                // Tangani error jika diperlukan
            }
        }
    }

    fun delete(favoriteEvent: FavoriteEvent) {
        viewModelScope.launch {
            try {
                mFavoriteEventRepository.delete(favoriteEvent)
            } catch (e: Exception) {
                // Tangani error jika diperlukan
            }
        }
    }

    fun update(favoriteEvent: FavoriteEvent) {
        viewModelScope.launch {
            try {
                mFavoriteEventRepository.update(favoriteEvent)
            } catch (e: Exception) {
                // Tangani error jika diperlukan
            }
        }
    }
}
