package com.example.nick.herexamen.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.nick.herexamen.database.RecipeRepository
import com.example.nick.herexamen.database.ShoppingAppDatabase
import com.example.nick.herexamen.model.Recipe


class RecipeViewModel(application: Application) : AndroidViewModel(application) {

    // @Inject
    private val recipeRepository: RecipeRepository
    val allRecipes: LiveData<List<Recipe>>

    init {
        val recipeDao = ShoppingAppDatabase.getDatabase(application).recipeDao()
        recipeRepository = RecipeRepository(recipeDao)
        allRecipes = recipeRepository.allRecipes
    }

    fun insert(recipe: Recipe) {
       recipeRepository.insert(recipe)
    }

}