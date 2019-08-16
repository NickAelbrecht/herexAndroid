package com.example.nick.herexamen.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.nick.herexamen.adapters.Simple
import com.example.nick.herexamen.database.App
import com.example.nick.herexamen.model.RecipeRepository
import com.example.nick.herexamen.model.Recipe
import com.example.nick.herexamen.netwerk.RecipeApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class RecipeViewModel : ViewModel() {

    /**
     * [recipesFromApi] LiveData van een lijst van recepten (backend database)
     */
    private val recipesFromApi = MutableLiveData<List<Recipe>>()
    /**
     * De [RecipeRepository] die wordt geinjecteerd
     */
    @Inject
    lateinit var recipeRepository: RecipeRepository
    /**
     * De [RecipeApi] die wordt geinjecteerd
     */
    @Inject
    lateinit var recipeApi: RecipeApi


    /**
     * Represents a disposable resources
     */
    private var subscription: Disposable


    init {
        App.component.inject(this)

        subscription = recipeApi.getAllRecipes()
            //we tell it to fetch the data on background by
            .subscribeOn(Schedulers.io())
            //we like the fetched data to be displayed on the MainTread (UI)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveRecipesStart() }
            .doOnTerminate { onRetrieveRecipesFinish() }
            .subscribe(
                { result -> onRetrieveRecipesSucces(result) },
                { error -> onRetrieveRecipesError(error) }
            )
    }
    /***********************/
    //      LOCAL CALLS    //
    /***********************/
    /**
     * [allRecipes]Livedata van een lijst van recepten (room database)
     */
    val allRecipes: LiveData<List<Recipe>> = recipeRepository.allRecipes

    /**
     * [insert] Voegt een recept toe aan de room database
     * @param recipe: Het toe te voegen recept
     */
    fun insert(recipe: Recipe) {
        recipeRepository.insert(recipe)
    }

    /**
     * [deleteByTitle] Verwijdert het recept met bepaald titel uit de room database
     * @param title: Titel van het te verwijderen recept
     */
    fun deleteByTitle(title: String) {
        recipeRepository.deleteByTitle(title)
    }

    /**
     * [findByTitle] Zoekt het recept met bepaalde titel in de room database
     * @param title: Titel van het te zoeken recept
     * @return Het recept met bepaalde titel
     */
    fun findByTitle(title: String): Recipe {
        return recipeRepository.findByTitle(title)
    }

    /**
     * [updateRecipe] Update het recept in de room database
     * @param recipe: Het up te daten recept
     */
    fun updateRecipe(recipe: Recipe) {
        recipeRepository.updateRecipe(recipe)
    }


    /***********************/
    //      API CALLS      //
    /***********************/


    private fun onRetrieveRecipesStart() {
        Log.i("VIEWMODEL", "Start retrieving")
    }

    private fun onRetrieveRecipesFinish() {
        Log.i("VIEWMODEL", "finish retrieving")
    }

    /**
     * [onRetrieveRecipesSucces] Wordt opgeroepen als het ophalen van de recepten uit de backend database succesvol was.
     * @param result: Het resultaat van het ophalen uit de database
     */
    private fun onRetrieveRecipesSucces(result: List<Recipe>) {
        Log.i("VIEWMODEL", result.toString())
        recipesFromApi.value = result
    }

    private fun onRetrieveRecipesError(error: Throwable) {
        Log.e("VIEWMODEL", error.message!!)
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    /**
     * [getRecipesFromApi] Geeft alle recepten van de backend database terug
     * @return Livedata van een lijst van [Recipe]
     */
    fun getRecipesFromApi(): LiveData<List<Recipe>> {
        subscription = recipeApi.getAllRecipes()
            //we tell it to fetch the data on background by
            .subscribeOn(Schedulers.io())
            //we like the fetched data to be displayed on the MainTread (UI)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveRecipesStart() }
            .doOnTerminate { onRetrieveRecipesFinish() }
            .subscribe(
                { result -> onRetrieveRecipesSucces(result) },
                { error -> onRetrieveRecipesError(error) }
            )
        return recipesFromApi
    }

    /**
     * [insertRecipeApi] Voegt een recept toe aan de backend database
     * @param recipe: Het toe te voegen recept
     * @return Het toegevoegde recept, wrapped in een [Simple]
     */
    fun insertRecipeApi(recipe: Recipe): Simple<Recipe> {
        return recipeApi.insertRecipe(recipe)
    }

    /**
     * [getRecipeByTitleApi] Zoekt het recept met bepaalde titel in de backend database
     * @param title: Titel van het te zoeken recept
     * @return Het recept met bepaalde titel, wrapped in een [Simple]
     */
    fun getRecipeByTitleApi(title: String): Simple<Recipe> {
        return recipeApi.getRecipeByTitle(title)
    }

    /**
     * [deleteByTitle] Verwijdert het recept met bepaald titel uit de backend database
     * @param title: Titel van het te verwijderen recept
     * @return Het recept met bepaalde titel, wrapped in een [Simple]
     */
    fun deleteRecipeByTitleApi(title: String): Simple<String> {
        return recipeApi.deleteByTitle(title)
    }

    /**
     * [updateRecipeApi] Update het recept in de backend database
     * @param recipe: Het up te daten recept
     * @return Het geupdate recept, wrapped in een [Simple]
     */
    fun updateRecipeApi(recipe: Recipe): Simple<Recipe> {
        return recipeApi.updateRecipe(recipe)
    }
}