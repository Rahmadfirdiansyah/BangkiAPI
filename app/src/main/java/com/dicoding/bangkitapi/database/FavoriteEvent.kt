package com.dicoding.bangkitapi.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteEvent(
    @PrimaryKey(autoGenerate = false)
    var id: String = "",
    var name: String = "",
    var ownerName: String = "",         // Menambahkan nama penyelenggara
    var quota: Int = 0,                 // Menambahkan kuota
    var registrant: Int = 0,            // Menambahkan registran
    var beginTime: String = "",          // Menambahkan waktu acara
    var description: String = "",        // Menambahkan deskripsi
    var mediaCover: String? = null,
    var link: String? = null  // Tambahkan properti link
) : Parcelable
