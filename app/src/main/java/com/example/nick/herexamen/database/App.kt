package com.example.nick.herexamen.database

import android.app.Application

class App : Application() {
    companion object {
      //  lateinit var component: DatabaseComponent
    }

    override fun onCreate() {
        super.onCreate()
      /*  component = DaggerDatabaseComponent
            .builder()
            .databaseModule(DatabaseModule(this))
            .build ()*/
    }
}