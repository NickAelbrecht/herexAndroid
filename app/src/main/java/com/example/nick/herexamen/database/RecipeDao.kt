package com.example.nick.herexamen.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.nick.herexamen.model.Recipe

@Dao
interface RecipeDao {

    @Insert
    fun insert(recipe:Recipe)

    @Query("DELETE from recipe_table")
    fun deleteAll()

    @Query("SELECT * from recipe_table ORDER BY title asc")
    fun getAllRecipes() : LiveData<List<Recipe>>

    @Query("SELECT * FROM recipe_table WHERE title LIKE :title")
    fun findByTitle(title: String): LiveData<List<Recipe>>
}