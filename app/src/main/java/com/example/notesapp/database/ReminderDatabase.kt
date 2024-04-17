package com.example.notesapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesapp.model.Reminder

@Database(entities = [Reminder::class], version = 1)
abstract class ReminderDatabase :RoomDatabase(){
    abstract fun getRemDao():ReminderDao
    companion object{
        @Volatile
        private var instance:ReminderDatabase?=null
        private var LOCK=Any()

        operator  fun invoke(context:Context)= instance?:
        synchronized(LOCK){
            instance ?:
            createDatabase(context).also{
                instance= it

            }
        }

        private fun createDatabase(context: Context)= Room.databaseBuilder(
            context.applicationContext,
            ReminderDatabase::class.java,
            "Reminder_db"

        ).build()


    }
}