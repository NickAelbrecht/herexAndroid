package com.example.nick.herexamen.injectie.component

import com.example.nick.herexamen.injectie.module.NetwerkModule
import com.example.nick.herexamen.viewmodels.RecipeViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetwerkModule::class])
interface ViewModelComponent {

    fun inject(recipeViewModel: RecipeViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelComponent

        fun networkModule(netwerkModule: NetwerkModule):Builder
    }

}