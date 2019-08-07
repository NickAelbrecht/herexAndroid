package com.example.nick.herexamen.injectie

import com.example.nick.herexamen.database.App
import com.example.nick.herexamen.viewmodels.RecipeViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class])
interface DatabaseComponent {

    fun inject(recipeViewModel: RecipeViewModel)

    fun inject(app: App)

}