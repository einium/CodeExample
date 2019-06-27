package com.example.employeeapp.dagger

import android.content.Context
import androidx.room.Room
import com.example.employeeapp.data.database.AppDatabase
import com.example.employeeapp.data.database.EmployeeDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(appDatabase: AppDatabase): EmployeeDao {
        return appDatabase.employeeDao()
    }
}