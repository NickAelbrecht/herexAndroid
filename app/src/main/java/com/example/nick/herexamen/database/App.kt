package com.example.nick.herexamen.database

import android.app.Application
import com.example.nick.herexamen.injectie.DatabaseComponent
import com.example.nick.herexamen.injectie.DatabaseModule
import com.example.nick.herexamen.injectie.DaggerDatabaseComponent

class App : Application() {
    companion object {
        lateinit var component: DatabaseComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerDatabaseComponent
            .builder()
            .databaseModule(DatabaseModule(this))
            .build ()
    }
}