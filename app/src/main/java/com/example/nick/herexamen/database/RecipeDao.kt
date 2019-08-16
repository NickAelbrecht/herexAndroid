package com.example.nick.herexamen.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.nick.herexamen.model.Recipe

@Dao
interface RecipeDao {
    /**
     * @param recipe : Recept dat moet toegevoegd worden in de room database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: Recipe)

    /**
     * Verwijdert alle recepten uit de room database
     */
    @Query("DELETE from recipe_table")
    fun deleteAll()

    /**
     * Haalt alle recepten uit de room database
     * @return LiveData een List met recepten
     */
    @Query("SELECT * from recipe_table ORDER BY title asc")
    fun getAllRecipes(): LiveData<List<Recipe>>

    /**
     * Zoekt het recept met bijhorende titel
     * @param title: Titel van het te zoeken recept
     * @return Het recept met de bepaalde titel
     */
    @Query("SELECT * FROM recipe_table WHERE title = :title")
    fun findByTitle(title: String): Recipe

    /**
     * Verwijdert het recept met de titel uit de room database
     * @param title: De titel van het te verwijderen recept
     */
    @Query("DELETE from recipe_table WHERE title LIKE :title")
    fun deleteByTitle(title: String)

    /**
     * Update het meegegeven recept
     * @param recipe: Het recept dat moet upgedate worden
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateRecipe(recipe: Recipe)

}