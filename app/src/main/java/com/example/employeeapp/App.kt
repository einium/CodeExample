package com.example.employeeapp

import android.app.Application
import androidx.room.Room
import com.example.employeeapp.dagger.*
import com.example.employeeapp.data.database.AppDatabase
import com.example.employeeapp.data.database.EmployeeDao

class App : Application() {
    private lateinit var dao: EmployeeDao
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