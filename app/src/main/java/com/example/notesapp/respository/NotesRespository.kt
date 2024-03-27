package com.example.notesapp.respository

import com.example.notesapp.model.Note
import com.example.notesapp.database.NoteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotesRespository(private val db: NoteDatabase) {
    suspend fun insertNote(note: Note) {
        withContext(Dispatchers.IO) {
            db.getNoteDao().insertNote(note)
        }
    }

    suspend fun deleteNote(note: Note) = withContext(Dispatchers.IO) {
        db.getNoteDao().deleteNote(note)
    }

    suspend fun updateNote(note: Note) = withContext(Dispatchers.IO) {
        db.getNoteDao().updateNote(note)
    }

     fun getAllNotes() = db.getNoteDao().getallnotes()

     fun searchNotes(query: String?) = db.getNoteDao().searchNote(query)
}