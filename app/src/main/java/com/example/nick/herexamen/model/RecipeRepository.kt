package com.example.nick.herexamen.model

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import com.example.nick.herexamen.database.RecipeDao
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult


class
RecipeRepository(private val recipeDao: RecipeDao) {

    val allRecipes: LiveData<List<Recipe>> = recipeDao.getAllRecipes()

    @WorkerThread
    fun insert(recipe: Recipe) {
        doAsync {
            recipeDao.insert(recipe)
        }
    }

    @WorkerThread
    fun deleteByTitle(title: String) {
        doAsync {
            recipeDao.deleteByTitle(title)
        }
    }

    @WorkerThread
    fun findByTitle(title: String): Recipe {
        return doAsyncResult {
            recipeDao.findByTitle(title)
        }.get()
    }

    @WorkerThread
    fun updateRecipe(recipe: Recipe) {
        doAsync {
            recipeDao.updateRecipe(recipe)
        }
    }
}