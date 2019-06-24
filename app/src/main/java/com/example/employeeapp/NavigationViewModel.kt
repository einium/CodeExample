package com.example.employeeapp

import androidx.lifecycle.ViewModel
import com.example.employeeapp.data.model.Employee
import com.example.employeeapp.data.model.Specialty
import com.example.employeeapp.view.NavigationListener

class NavigationViewModel  : ViewModel() {

    private var navigationListener: NavigationListener? = null

    fun addNavigationListener(navListener: NavigationListener) {
        navigationListener = navListener
    }

    fun removeNavigationListener() {
        navigationListener = null
    }

    fun onSpecialtyItemClick() {
        navigationListener?.navigateToEmployeeList()
    }

    fun onEmployeeItemClick() {
        navigationListener?.navigateToEmployee()
    }
}