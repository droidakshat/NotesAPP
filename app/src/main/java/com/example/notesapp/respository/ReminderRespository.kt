package com.example.notesapp.respository

import com.example.notesapp.model.Note
import com.example.notesapp.database.NoteDatabase
import com.example.notesapp.database.ReminderDatabase
import com.example.notesapp.model.Reminder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReminderRespository(private val db: ReminderDatabase) {
    suspend fun insertRem(reminder: Reminder) {
        withContext(Dispatchers.IO) {
            db.getRemDao().insertRem(reminder)
        }
    }

    suspend fun deleteRem(reminder: Reminder) = withContext(Dispatchers.IO) {
        db.getRemDao().deleteRem(reminder)
    }

    suspend fun updateRem(reminder: Reminder) = withContext(Dispatchers.IO) {
        db.getRemDao().updateRem(reminder)
    }

     fun getAllRem() = db.getRemDao().getallReminder()

 }