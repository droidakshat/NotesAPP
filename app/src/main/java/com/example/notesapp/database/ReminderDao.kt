package com.example.notesapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.notesapp.model.Reminder

@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRem(reminder: Reminder)

    @Update
    suspend fun updateRem(reminder: Reminder)

    @Delete
    suspend fun deleteRem(reminder: Reminder)

    @Query("SELECT * FROM NOTES ORDER BY id DESC")
    fun getallReminder():LiveData<List<Reminder>>
//
//    @Query("SELECT * FROM NOTES WHERE noteTile LIKE :query OR notedesc LIKE :query")
//    fun searchNote(query: String?): LiveData<List<Note>>
}