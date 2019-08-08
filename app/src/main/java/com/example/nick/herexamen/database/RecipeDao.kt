package com.example.nick.herexamen.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.nick.herexamen.model.Recipe

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: Recipe)

    @Query("DELETE from recipe_table")
    fun deleteAll()

    @Query("SELECT * from recipe_table ORDER BY title asc")
    fun getAllRecipes(): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipe_table WHERE title LIKE :title")
    fun findByTitle(title: String): LiveData<Recipe>

    @Query("DELETE from recipe_table WHERE title LIKE :title")
    fun deleteByTitle(title: String)

    @Update
    fun updateRecipe(recipe: Recipe)

}