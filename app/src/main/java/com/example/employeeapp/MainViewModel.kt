package com.example.employeeapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val fullEmployeeList = ArrayList<Employee>()

    private val currentFragmentLiveData = MutableLiveData<FragmentName>()
    fun getCurrentFragmentLiveData(): LiveData<FragmentName> {
        return currentFragmentLiveData
    }

    private val loadingLiveData = MutableLiveData<Boolean>()
    fun getLoadingLiveData(): LiveData<Boolean> {
        return loadingLiveData
    }

    private val errorLiveData = MutableLiveData<String>()
    fun getErrorLiveData(): LiveData<String> {
        return errorLiveData
    }

    private val specialtyListLiveData = MutableLiveData<List<Specialty>>()
    fun getSpecialtyListLiveData(): LiveData<List<Specialty>> {
        return specialtyListLiveData
    }

    private val employeeBySpecialtyListLiveData = MutableLiveData<List<Employee>>()
    fun getEmployeeBySpecialtyListLiveData(): LiveData<List<Employee>> {
        return employeeBySpecialtyListLiveData
    }

    private var currentSpecialty: Specialty? = null
    private var currentEmployee: Employee? = null
    private var repository: EmployeeRepository? = null

    fun loadData(repo: EmployeeRepository) {
        if (repository == null) {
            repository = repo
        }
        repository?.loadData(object : LoadCallback {
            override fun startLoad() {
                loadingLiveData.postValue(true)
            }

            override fun onEmployeesLoaded(employeeList: List<Employee>) {
                loadingLiveData.postValue(false)

                fullEmployeeList.clear()
                fullEmployeeList.addAll(employeeList)

                if (currentFragmentLiveData.value == null) {
                    currentFragmentLiveData.postValue(FragmentName.SpecialityListFragment)
                }
                updateEmployeeBySpecialtyList()
                updateSpecialtyList()
            }

            override fun onError(message: String) {
                errorLiveData.postValue(message)
            }
        })
    }

    private fun updateEmployeeBySpecialtyList() {
        val specialty = currentSpecialty
        val resultList = ArrayList<Employee>()
        if (specialty != null) {
            for (employee in fullEmployeeList) {
                if (employee.specialty[0].specialty_id == specialty.specialty_id) {
                    resultList.add(employee)
                }
            }
        }
        employeeBySpecialtyListLiveData.postValue(resultList)
    }

    private fun updateSpecialtyList() {
        val specialtySet = LinkedHashSet<Specialty>()
        for (employee in fullEmployeeList) {
            specialtySet.add(employee.specialty[0])
        }
        specialtyListLiveData.postValue(specialtySet.toList())
    }

    fun onSpecialtyItemClick(specialty: Specialty) {
        currentSpecialty = specialty
        currentFragmentLiveData.postValue(FragmentName.EmployeeListFragment)
        updateEmployeeBySpecialtyList()
    }

    fun onEmployeeItemClick(employee: Employee) {
        currentEmployee = employee
        currentFragmentLiveData.postValue(FragmentName.EmployeeFragment)
    }

    fun getCurrentEmployee(): Employee? {
        return currentEmployee
    }
}

enum class FragmentName {
    SpecialityListFragment, EmployeeListFragment, EmployeeFragment
}