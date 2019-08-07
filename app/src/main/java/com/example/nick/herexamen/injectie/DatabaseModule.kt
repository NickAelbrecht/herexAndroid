package com.example.nick.herexamen.injectie

import android.app.Application
import android.content.Context
import com.example.nick.herexamen.database.RecipeDao
import com.example.nick.herexamen.model.RecipeRepository
import com.example.nick.herexamen.database.ShoppingAppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(private val application: Application) {


    /*@Provides
    internal fun provideRetrofitInterface(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }*/

    /*@Provides
    internal fun provideRecipeApi(retrofit: Retrofit): RecipeApi{
        return retrofit.create(RecipeApi::class.java)
    }*/



    @Provides
    @Singleton
    internal fun provideRecipeRepository(recipeDao: RecipeDao): RecipeRepository {
        return RecipeRepository(recipeDao)
    }

    @Provides
    @Singleton
    internal fun provideRecipeDao(shoppingAppDatabase: ShoppingAppDatabase): RecipeDao {
        return shoppingAppDatabase.recipeDao()
    }

    @Provides
    @Singleton
    internal fun provideshoppingAppDatabase(context: Context): ShoppingAppDatabase {
        return ShoppingAppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return application
    }
}