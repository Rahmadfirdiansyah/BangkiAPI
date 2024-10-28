package com.dicoding.bangkitapi.database

import android.content.Context
import android.provider.CalendarContract.Instances
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [FavoriteEvent::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun FavoriteEventDao():FavoriteEventDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): AppDatabase{
            if (INSTANCE == null){
                synchronized(RoomDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "favorite_event_database")
                        .build()
                }
            }
            return INSTANCE as AppDatabase
        }
    }

}