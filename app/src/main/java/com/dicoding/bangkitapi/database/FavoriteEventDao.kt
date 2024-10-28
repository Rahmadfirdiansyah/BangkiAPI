package com.dicoding.bangkitapi.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteEventDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteEvent: FavoriteEvent)

    @Delete
    fun delete(favoriteEvent: FavoriteEvent)

    @Update  // Tambahkan anotasi ini
    fun update(favoriteEvent: FavoriteEvent)

    @Query("SELECT * FROM FavoriteEvent ORDER BY id ASC")
    fun getAllFavoriteEvents(): LiveData<List<FavoriteEvent>>

    // Query untuk mendapatkan event favorit berdasarkan ID
    @Query("SELECT * FROM FavoriteEvent WHERE id = :id")
    fun getFavoriteEventById(id: String): LiveData<FavoriteEvent>

//    @Query("SELECT * from favoriteevent ORDER BY id ASC")
//    fun getAllFavorite(): List<FavoriteEvent>
}