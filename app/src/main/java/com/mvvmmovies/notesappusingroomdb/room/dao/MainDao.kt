package com.mvvmmovies.notesappusingroomdb.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mvvmmovies.notesappusingroomdb.room.entity.Notes

@Dao
interface MainDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(notes: Notes)

    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAll(): MutableList<Notes>

    @Query("UPDATE notes SET title = :title,notes = :notes WHERE id = :id")
    fun update(id: Int, title: String, notes: String)

    @Delete
    fun delete(notes: Notes)

    @Query("UPDATE notes SET pinned = :pin WHERE id = :id")
    fun pin(id: Int, pin: Boolean)
}