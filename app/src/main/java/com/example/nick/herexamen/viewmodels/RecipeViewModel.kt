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

    val recipesFromApi = MutableLiveData<List<Recipe>>()

    @Inject
    lateinit var recipeRepository: RecipeRepository

    @Inject
    lateinit var recipeApi: RecipeApi
    private val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()

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
    val allRecipes: LiveData<List<Recipe>> = recipeRepository.allRecipes

    fun insert(recipe: Recipe) {
        recipeRepository.insert(recipe)
    }

    fun deleteByTitle(title: String) {
        recipeRepository.deleteByTitle(title)
    }

    fun findByTitle(title: String): Recipe {
        return recipeRepository.findByTitle(title)
    }

    fun updateRecipe(recipe: Recipe) {
        recipeRepository.updateRecipe(recipe)
    }


    /***********************/
    //      API CALLS      //
    /***********************/


    private fun onRetrieveRecipesStart() {
        Log.i("VIEWMODEL", "Start retrieving")
        loadingVisibility.value = true
    }

    private fun onRetrieveRecipesFinish() {
        Log.i("VIEWMODEL", "finish retrieving")
        loadingVisibility.value = false
    }

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


    fun getRecipesFromApi(): LiveData<List<Recipe>> {
        return recipesFromApi
    }

    fun insertRecipeApi(recipe: Recipe): Simple<Recipe> {
        return recipeApi.insertRecipe(recipe)
    }

    fun getRecipeByTitleApi(title: String): Simple<Recipe> {
        return recipeApi.getRecipeByTitle(title)
    }

    fun deleteRecipeByTitleApi(title: String): Simple<String> {
        return recipeApi.deleteByTitle(title)
    }

    fun updateRecipeApi(recipe: Recipe): Simple<Recipe> {
        return recipeApi.updateRecipe(recipe)
    }
}