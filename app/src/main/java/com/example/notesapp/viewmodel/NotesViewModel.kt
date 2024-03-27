package com.example.notesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.model.Note

import com.example.notesapp.respository.NotesRespository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(app:Application,private val notesRespository: NotesRespository):AndroidViewModel(app) {

    fun addNote(note: Note)=
        viewModelScope.launch {
            notesRespository.insertNote(note)
        }
    fun deleteNote(note: Note)=
        viewModelScope.launch {
            notesRespository.deleteNote(note)
        }
    fun updateNote(note: Note)=
        viewModelScope.launch {
            notesRespository.updateNote(note)
        }
    fun getallnotes()=notesRespository.getAllNotes()
    fun searchNotes(query:String?)=notesRespository.searchNotes(query)
}