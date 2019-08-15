package com.example.nick.herexamen.injectie

import android.app.Application
import android.content.Context
import com.example.nick.herexamen.adapters.SimpleCallAdapterFactory
import com.example.nick.herexamen.database.RecipeDao
import com.example.nick.herexamen.model.RecipeRepository
import com.example.nick.herexamen.database.ShoppingAppDatabase
import com.example.nick.herexamen.netwerk.RecipeApi
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class DatabaseModule(private val application: Application) {

    val BASE_URL = "http://10.0.2.2:3000/"

    @Provides
    internal fun provideRetrofitInterface(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addCallAdapterFactory(SimpleCallAdapterFactory.create())
            .build()
    }

    @Provides
    internal fun provideRecipeApi(retrofit: Retrofit): RecipeApi {
        return retrofit.create(RecipeApi::class.java)
    }

    /**
     * Returns the OkHttpClient
     */
    @Provides
    internal fun provideOkHttpClient(): OkHttpClient {
        //To debug Retrofit/OkHttp we can intercept the calls and log them.
        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
        }.build()
    }


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