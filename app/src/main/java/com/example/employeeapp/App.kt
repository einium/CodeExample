package com.example.employeeapp

import android.app.Application
import com.example.employeeapp.dagger.*

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        buildComponents()
    }

    private fun buildComponents() {
        val contextModule = ContextModule(this)
        appComponent = DaggerAppComponent.builder()
            .contextModule(contextModule)
            .build()
    }

    companion object {
        private lateinit var appComponent : AppComponent
        fun getAppComponent() : AppComponent {
            return appComponent
        }
    }
}