package com.example.nick.herexamen.model

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import com.example.nick.herexamen.database.RecipeDao
import org.jetbrains.anko.doAsync


class
 RecipeRepository(private val recipeDao: RecipeDao) {

    val allRecipes: LiveData<List<Recipe>> = recipeDao.getAllRecipes()

    @WorkerThread
    fun insert(recipe: Recipe) {
        doAsync {
            recipeDao.insert(recipe)
        }
    }
}