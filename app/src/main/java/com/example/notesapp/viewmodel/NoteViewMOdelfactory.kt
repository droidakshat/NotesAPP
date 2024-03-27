package com.example.notesapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.respository.NotesRespository

class NoteViewMOdelfactory(val app:Application,private val notesRespository: NotesRespository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotesViewModel(app, notesRespository) as T
    }

}