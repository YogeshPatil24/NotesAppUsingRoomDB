package com.mvvmmovies.notesappusingroomdb.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "notes")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "notes")
    var notes: String = "",

    @ColumnInfo(name = "date")
    var date: String = "",

    @ColumnInfo(name = "pinned")
    var pinned: Boolean = false
) : Serializable