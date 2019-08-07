package com.example.nick.herexamen.database

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.example.nick.herexamen.database.converter.Converter
import com.example.nick.herexamen.model.Recipe
//import org.jetbrains.anko.doAsync

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
                ).addCallback(object : RoomDatabase.Callback() {
                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                       // doAsync {
                         //   populateDatabase(INSTANCE!!.recipeDao())
                       // }
                    }
                })

                    .build()
                INSTANCE = instance
                return instance
            }
        }

        fun populateDatabase(recipeDao: RecipeDao) {
            var recipe = Recipe("Croques", listOf("Kaas", "Hesp", "Brood"), listOf("Gluten"), "Brood")
            recipeDao.insert(recipe)
            recipe = Recipe("Smos", listOf("Kaas", "Hesp", "Brood", "Tomaten", "Wortels"), listOf("Gluten"), "Brood")
            recipeDao.insert(recipe)
            recipe = Recipe("Hespenrolletjes", listOf("Kaas", "Hesp", "Witloof", "Melk"), listOf("Geen"), "Warm eten")
            recipeDao.insert(recipe)
            recipe = Recipe(
                "Spaghetti Bolognaise",
                listOf("Geraspte kaas", "Spaghetti", "Tomaten", "Gehakt", "Wortels", "Paprika's"),
                listOf("Gluten"),
                "Warm eten"
            )
            recipeDao.insert(recipe)
        }
    }
}