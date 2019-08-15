package com.example.nick.herexamen.netwerk

import com.example.nick.herexamen.adapters.Simple
import com.example.nick.herexamen.model.Recipe
import io.reactivex.Observable
import retrofit2.http.*

interface RecipeApi {


    @GET("recipes")
    fun getAllRecipes(): Observable<List<Recipe>>

    @POST("recipes")
    fun insertRecipe(@Body recipe: Recipe): Simple<Recipe>

    @GET("recipes/{title}")
    fun getRecipeByTitle(@Path("title") title: String): Simple<Recipe>

    @PUT("recipes")
    fun updateRecipe(@Body recipe: Recipe): Simple<Recipe>

    @DELETE("recipes/{title}")
    fun deleteByTitle(@Path("title") title: String): Simple<String>
}