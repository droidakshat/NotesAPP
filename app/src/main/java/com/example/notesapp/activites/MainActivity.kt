package com.example.notesapp.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.notesapp.R
import com.example.notesapp.database.NoteDatabase
import com.example.notesapp.databinding.ActivityMainBinding
import com.example.notesapp.databinding.FragmentEditBinding
import com.example.notesapp.fragment.HomeFragment
import com.example.notesapp.fragment.reminder.ReminderFragment
import com.example.notesapp.respository.NotesRespository
import com.example.notesapp.viewmodel.NoteViewMOdelfactory
import com.example.notesapp.viewmodel.NotesViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var notesViewModel: NotesViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setViewModel()


    }

    private fun setViewModel() {
        val notesRepository = NotesRespository(NoteDatabase(this))
        val viewModelFactory = NoteViewMOdelfactory(application, notesRepository)
        notesViewModel = ViewModelProvider(this, viewModelFactory).get(NotesViewModel::class.java)
    }


    }

