package com.example.nick.herexamen.database

import android.arch.lifecycle.LiveData
import com.example.nick.herexamen.model.Recipe

class RecipeRepository(private val recipeDao: RecipeDao) {
    val allQuestions:LiveData<List<Recipe>> = recipeDao.getAllRecipes()
}