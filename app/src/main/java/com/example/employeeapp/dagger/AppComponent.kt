package com.example.employeeapp.dagger

import com.example.employeeapp.MainViewModel
import com.example.employeeapp.data.EmployeeRepository
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ContextModule::class, RepositoryModule::class])
@Singleton
interface AppComponent {
    fun inject(mainViewModel: MainViewModel)
    fun inject(employeeRepository: EmployeeRepository)
}