package com.example.nick.herexamen.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.example.nick.herexamen.database.converter.Converter
import com.example.nick.herexamen.model.Recipe

@Database(entities = [Recipe::class], version = 1)
@TypeConverters(Converter::class)
abstract class ShoppingAppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: ShoppingAppDatabase? = null

        fun getDatabase(context: Context): ShoppingAppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShoppingAppDatabase::class.java,
                    "Shoppingcart_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}