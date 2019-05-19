package com.example.employeeapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val employeeListLiveData = MutableLiveData<List<Employee>>()
    fun getEmployeeListLiveData() : LiveData<List<Employee>> {
        return employeeListLiveData
    }
    private val currentFragmentLiveData = MutableLiveData<FragmentName>()
    fun getCurrentFragmentLiveData() : LiveData<FragmentName> {
        return currentFragmentLiveData
    }
    private val loadingLiveData = MutableLiveData<Boolean>()
    fun getLoadingLiveData() : LiveData<Boolean> {
        return loadingLiveData
    }
    private val errorLiveData = MutableLiveData<String>()
    fun getErrorLiveData() : LiveData<String> {
        return errorLiveData
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
                employeeListLiveData.postValue(employeeList)
                if (currentFragmentLiveData.value == null){
                    currentFragmentLiveData.postValue(FragmentName.SpecialityListFragment)
                }
            }

            override fun onError(message: String) {
                errorLiveData.postValue(message)
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
        currentFragmentLiveData.postValue(FragmentName.EmployeeListFragment)
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
        currentFragmentLiveData.postValue(FragmentName.EmployeeFragment)
    }

    fun getCurrentEmployee() : Employee? {
        return currentEmployee
    }
}
enum class FragmentName{
    SpecialityListFragment, EmployeeListFragment, EmployeeFragment
}