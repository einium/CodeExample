package com.example.employeeapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val employeeListLiveData = MutableLiveData<List<Employee>>()
    val currentFragment = MutableLiveData<FragmentName>()
    private var currentSpecialty: Specialty? = null
    private var currentEmployee: Employee? = null
    private var repository: Repository? = null

    fun loadData(repo: Repository) {
        if (repository == null) {
            repository = repo
        }
        repository?.loadData(object : LoadCallback {
            override fun onEmployeesLoaded(employeeList: List<Employee>) {
                employeeListLiveData.postValue(employeeList)
                if (currentFragment.value == null){
                    currentFragment.postValue(FragmentName.SpecialityListFragment)
                }
            }
        })
    }

    fun getSpecialtyList() : List<Specialty>{
        val employeeList = employeeListLiveData.value
        val specialtySet = LinkedHashSet<Specialty>()
        if (employeeList != null) {
            for (employee in employeeList) {
                specialtySet.add(employee.specialty[0])
            }
        }
        return specialtySet.toList()
    }

    fun onSpecialtyItemClick(specialty: Specialty) {
        currentSpecialty = specialty
        currentFragment.postValue(FragmentName.EmployeeListFragment)
    }

    fun getEmployeeList(): List<Employee> {
        val resultList = ArrayList<Employee>()
        val employeeList = employeeListLiveData.value
        val specialty = currentSpecialty
        if (specialty != null && employeeList != null) {
            for (employee in employeeList) {
                if (employee.specialty[0].specialty_id == specialty.specialty_id) {
                    resultList.add(employee)
                }
            }
        }
        return resultList
    }

    fun onEmployeeItemClick(employee: Employee) {
        currentEmployee = employee
        currentFragment.postValue(FragmentName.EmployeeFragment)
    }

    fun getCurrentEmployee() : Employee? {
        return currentEmployee
    }
}
enum class FragmentName{
    SpecialityListFragment, EmployeeListFragment, EmployeeFragment
}