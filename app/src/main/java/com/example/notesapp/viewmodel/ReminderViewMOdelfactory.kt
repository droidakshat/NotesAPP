package com.example.notesapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.respository.NotesRespository
import com.example.notesapp.respository.ReminderRespository

class ReminderViewMOdelfactory(val app:Application, private val reminderRespository:ReminderRespository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ReminderViewModel(app, reminderRespository) as T
    }

}