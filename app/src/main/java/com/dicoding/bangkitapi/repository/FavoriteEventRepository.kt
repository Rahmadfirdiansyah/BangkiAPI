package com.dicoding.bangkitapi.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.bangkitapi.database.AppDatabase
import com.dicoding.bangkitapi.database.FavoriteEvent
import com.dicoding.bangkitapi.database.FavoriteEventDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriteEventRepository(application: Application) {

    private val mFavoriteEventDao: FavoriteEventDao = AppDatabase.getDatabase(application).FavoriteEventDao()

    fun getAllFavoriteEvents(): LiveData<List<FavoriteEvent>> = mFavoriteEventDao.getAllFavoriteEvents()

    fun getFavoriteEventById(id: String): LiveData<FavoriteEvent> = mFavoriteEventDao.getFavoriteEventById(id)

    // Menggunakan coroutine untuk melakukan operasi database
    suspend fun insert(favoriteEvent: FavoriteEvent) {
        withContext(Dispatchers.IO) {
            mFavoriteEventDao.insert(favoriteEvent)
        }
    }

    suspend fun delete(favoriteEvent: FavoriteEvent) {
        withContext(Dispatchers.IO) {
            mFavoriteEventDao.delete(favoriteEvent)
        }
    }

    suspend fun update(favoriteEvent: FavoriteEvent) {
        withContext(Dispatchers.IO) {
            mFavoriteEventDao.update(favoriteEvent)
        }
    }
}
