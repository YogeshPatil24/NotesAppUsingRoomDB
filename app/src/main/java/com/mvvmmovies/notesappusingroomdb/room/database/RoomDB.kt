package com.mvvmmovies.notesappusingroomdb.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mvvmmovies.notesappusingroomdb.room.dao.MainDao
import com.mvvmmovies.notesappusingroomdb.room.entity.Notes

@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class RoomDB : RoomDatabase() {
    companion object {
        private var database: RoomDB? = null
        private var DATABASE_NAME = "NoteApp"

        @Synchronized
        fun getInstance(context: Context): RoomDB {
            if (database == null) {
                synchronized(this) {
                    if (database == null) {
                        database = Room.databaseBuilder(
                            context.applicationContext,
                            RoomDB::class.java,
                            DATABASE_NAME
                        )
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return database!!
        }
    }

    abstract fun mainDao(): MainDao

}
