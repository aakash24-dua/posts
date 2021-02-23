package com.example.movie

import android.app.Application
import com.example.movie.utils.AppComponent
import com.example.movie.utils.AppModule
import com.example.movie.utils.DaggerAppComponent

class App : Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }
}