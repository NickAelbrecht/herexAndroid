package com.example.nick.herexamen.database

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

//@Database(entities = [Word::class], version = 1)
abstract class QuizDatabase : RoomDatabase() {
    //abstract fun wordDao(): WordDao

    companion object {
        @Volatile
        private var INSTANCE: QuizDatabase? = null

        fun getDatabase(context: Context): QuizDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuizDatabase::class.java,
                    "Quiz_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}