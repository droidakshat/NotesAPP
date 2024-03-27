package com.example.notesapp.model

import androidx.room.PrimaryKey
import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Entity(tableName = "notes")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val noteTile:String,
    val notedesc:String

) :Parcelable
