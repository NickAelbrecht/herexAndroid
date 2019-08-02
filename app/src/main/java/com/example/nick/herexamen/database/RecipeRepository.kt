package com.example.nick.herexamen.database

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import com.example.nick.herexamen.model.Recipe

class
 RecipeRepository(private val recipeDao: RecipeDao) {

    val allRecipes: LiveData<List<Recipe>> = recipeDao.getAllRecipes()

    @WorkerThread
    fun insert(recipe: Recipe) {
        recipeDao.insert(recipe)
    }
}