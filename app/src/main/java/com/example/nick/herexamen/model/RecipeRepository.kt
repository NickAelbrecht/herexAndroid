package com.example.nick.herexamen.model

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import com.example.nick.herexamen.database.RecipeDao
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult


/**
 * De repository dat de tussenlaag is tussen de DAO en het viewmodel.
 */
class
RecipeRepository(private val recipeDao: RecipeDao) {

    val allRecipes: LiveData<List<Recipe>> = recipeDao.getAllRecipes()

    /**
     * [insert] Voegt een bepaald recept toe aan de roomdatabase
     * @param recipe: Het recept dat wordt toegevoegd
     */
    @WorkerThread
    fun insert(recipe: Recipe) {
        doAsync {
            recipeDao.insert(recipe)
        }
    }

    /**
     * [deleteByTitle] Verwijdert het recept met bepaalde titel
     * @param title: De titel van het te verwijderen recept
     */
    @WorkerThread
    fun deleteByTitle(title: String) {
        doAsync {
            recipeDao.deleteByTitle(title)
        }
    }

    /**
     * [findByTitle] Zoekt een recept met bepaalde titel
     * @param title: De titel van het te zoeken recept
     * @return Het te zoeken recept met die titel
     */
    @WorkerThread
    fun findByTitle(title: String): Recipe {
        return doAsyncResult {
            recipeDao.findByTitle(title)
        }.get()
    }

    /**
     * [updateRecipe] Update een bepaald recept
     * @param recipe: Het up te daten recept
     */
    @WorkerThread
    fun updateRecipe(recipe: Recipe) {
        doAsync {
            recipeDao.updateRecipe(recipe)
        }
    }
}