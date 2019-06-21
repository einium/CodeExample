package com.example.employeeapp.dagger

import com.example.employeeapp.view.EmployeeFragment
import com.example.employeeapp.view.EmployeeListFragment
import com.example.employeeapp.view.SpecialityListFragment
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FragmentModule {
    @Provides
    @Singleton
    fun provideSpecialityListFragment() : SpecialityListFragment{
        return SpecialityListFragment()
    }

    @Provides
    @Singleton
    fun provideEmployeeListFragment() : EmployeeListFragment {
        return EmployeeListFragment()
    }

    @Provides
    @Singleton
    fun provideEmployeeFragment() : EmployeeFragment {
        return EmployeeFragment()
    }
}
