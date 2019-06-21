package com.example.employeeapp.dagger

import com.example.employeeapp.view.MainActivity
import dagger.Component
import javax.inject.Singleton


@Component(modules = [FragmentModule::class])
@Singleton
interface ViewComponent {
    fun inject(mainActivity: MainActivity)
}