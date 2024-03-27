package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.database.NoteDatabase
import com.example.notesapp.respository.NotesRespository
import com.example.notesapp.viewmodel.NoteViewMOdelfactory
import com.example.notesapp.viewmodel.NotesViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var notesViewModel: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setViewModel()
    }

    private fun setViewModel() {
        val notesRepository = NotesRespository(NoteDatabase(this))
        val viewModelFactory = NoteViewMOdelfactory(application, notesRepository)
        notesViewModel = ViewModelProvider(this, viewModelFactory).get(NotesViewModel::class.java)
    }
}
