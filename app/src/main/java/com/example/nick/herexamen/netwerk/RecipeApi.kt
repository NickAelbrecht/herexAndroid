package com.example.nick.herexamen.netwerk

import com.example.nick.herexamen.model.Recipe
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

interface RecipeApi {


    @GET("recipes")
    fun getAllRecipes(): Observable<List<Recipe>>

    @POST("recipes")
    fun insertRecipe(@Body request:RequestBody)

    @GET("recipes/{title}")
    fun getRecipeByTitle(@Path("title") title: String): Observable<Recipe>

    @PUT("recipes")
    fun updateRecipe(@Body request: RequestBody)

    @DELETE("recipes/{title")
    fun deleteByTitle(@Path("title") title: String)


}