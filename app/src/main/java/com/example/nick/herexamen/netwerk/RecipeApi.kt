package com.example.nick.herexamen.netwerk

import com.example.nick.herexamen.model.Recipe
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface RecipeApi {


    @GET("recipes")
    fun getAllRecipes(): Observable<List<Recipe>>

    @POST("recipes")
    fun insertRecipe(@Body recipe:Recipe) : Call<Recipe>

    @GET("recipes/{title}")
    fun getRecipeByTitle(@Path("title") title: String): Observable<Recipe>

    @PUT("recipes")
    fun updateRecipe(@Body recipe: Recipe)

    @DELETE("recipes/{title")
    fun deleteByTitle(@Path("title") title: String)
}