package com.example.nick.herexamen.netwerk

import com.example.nick.herexamen.adapters.Simple
import com.example.nick.herexamen.model.Recipe
import io.reactivex.Observable
import retrofit2.http.*

/**
 * [RecipeApi] Is de link tussen de front- en de backend
 */
interface RecipeApi {

    /**
     * [getAllRecipes] Geeft alle recepten van de database terug
     * @return Een observeerbare lijst van [Recipe]
     */
    @GET("recipes")
    fun getAllRecipes(): Observable<List<Recipe>>

    /**
     * [insertRecipe] Voegt een bepaald recept toe aan de database
     * @return Het recept dat werd toegevoegd
     * Er wordt met de [Simple] klasse gewerkt voor te vermijden dat er via 'Call' moet gewerkt worden
     */
    @POST("recipes")
    fun insertRecipe(@Body recipe: Recipe): Simple<Recipe>

    /**
     * [getRecipeByTitle] Haalt een recept met bepaalde titel op van de database
     * @param title: De titel van het op te halen recept
     * @return Het opgehaalde recept, wrapped in een [Simple]
     */
    @GET("recipes/{title}")
    fun getRecipeByTitle(@Path("title") title: String): Simple<Recipe>

    /**
     * [updateRecipe] Update een bepaald recept
     * @param recipe: Het up te daten recept
     * @return Het upgedate recept, wrapped in een [Simple]
     */
    @PUT("recipes")
    fun updateRecipe(@Body recipe: Recipe): Simple<Recipe>

    /**
     * [deleteByTitle] Verwijdert een recept uit de database met bepaalde titel
     * @param title: De titel van het te verwijderen recept
     * @return Een string, wrapped in een [Simple]
     */
    @DELETE("recipes/{title}")
    fun deleteByTitle(@Path("title") title: String): Simple<String>
}