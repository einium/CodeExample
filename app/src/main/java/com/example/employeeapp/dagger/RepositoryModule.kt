package com.example.employeeapp.dagger

import android.content.Context
import com.example.employeeapp.data.DataBaseHelper
import com.example.employeeapp.data.EmployeeRepository
import com.example.employeeapp.data.Repository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepo() : Repository {
        return EmployeeRepository()
    }

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://gitlab.65apps.com/")
            .build()
    }

    @Provides
    @Singleton
    fun provideDataBaseHelper(context: Context): DataBaseHelper {
        return DataBaseHelper(context)
    }
}