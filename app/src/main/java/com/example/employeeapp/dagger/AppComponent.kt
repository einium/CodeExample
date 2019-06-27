package com.example.employeeapp.dagger

import com.example.employeeapp.DataViewModel
import com.example.employeeapp.data.EmployeeRepository
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ContextModule::class, RepositoryModule::class, DatabaseModule::class])
@Singleton
interface AppComponent {
    fun inject(mainViewModel: DataViewModel)
    fun inject(employeeRepository: EmployeeRepository)
}