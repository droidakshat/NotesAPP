package com.example.notesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.model.Note
import com.example.notesapp.model.Reminder

import com.example.notesapp.respository.NotesRespository
import com.example.notesapp.respository.ReminderRespository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReminderViewModel(app:Application, private val reminderRespository: ReminderRespository):AndroidViewModel(app) {

    fun addRem(reminder: Reminder)=
        viewModelScope.launch {
            reminderRespository.insertRem(reminder)
        }
    fun deleteRem(reminder: Reminder)=
        viewModelScope.launch {
            reminderRespository.deleteRem(reminder)
        }
    fun updateRem(reminder: Reminder)=
        viewModelScope.launch {
            reminderRespository.updateRem(reminder)
        }
    fun getallRem()=reminderRespository.getAllRem()
 }