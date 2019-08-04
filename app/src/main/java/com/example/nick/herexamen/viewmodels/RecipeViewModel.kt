package com.example.nick.herexamen.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.nick.herexamen.database.App
import com.example.nick.herexamen.model.RecipeRepository
import com.example.nick.herexamen.model.Recipe
import javax.inject.Inject


class RecipeViewModel : ViewModel(){

    @Inject
    lateinit var recipeRepository: RecipeRepository


    init {
        App.component.inject(this)
    }
    val allRecipes: LiveData<List<Recipe>> = recipeRepository.allRecipes

    fun insert(recipe: Recipe) {
       recipeRepository.insert(recipe)
    }

}