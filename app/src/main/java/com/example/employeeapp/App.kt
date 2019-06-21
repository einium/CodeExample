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
        viewComponent = DaggerViewComponent.builder()
            .build()
    }

    companion object {
        private lateinit var appComponent : AppComponent
        fun getAppComponent() : AppComponent {
            return appComponent
        }
        private lateinit var viewComponent : ViewComponent
        fun getViewComponent() : ViewComponent {
            return viewComponent
        }
    }
}