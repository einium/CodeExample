package com.example.employeeapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.employeeapp.data.EmployeeRepository
import com.example.employeeapp.data.LoadCallback
import com.example.employeeapp.data.model.Employee
import com.example.employeeapp.data.model.Specialty
import com.example.employeeapp.view.NavigationListener
import javax.inject.Inject

class MainViewModel : ViewModel() {
    init{
        App.getAppComponent().inject(this)
    }

    private val fullEmployeeList = ArrayList<Employee>()

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

    @Inject
    lateinit var repository: EmployeeRepository

    fun loadData() {
        repository.loadData(object : LoadCallback {
            override fun startLoad() {
                loadingLiveData.postValue(true)
            }

            override fun onEmployeesLoaded(employeeList: List<Employee>) {
                loadingLiveData.postValue(false)

                fullEmployeeList.clear()
                fullEmployeeList.addAll(employeeList)

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
        updateEmployeeBySpecialtyList()
        navigationListener?.navigateToEmployeeList()
    }

    fun onEmployeeItemClick(employee: Employee) {
        currentEmployee = employee
        navigationListener?.navigateToEmployee()
    }

    fun getCurrentEmployee(): Employee? {
        return currentEmployee
    }

    private var navigationListener: NavigationListener? = null

    fun addNavigationListener(navListener: NavigationListener) {
        navigationListener = navListener
    }

    fun removeNavigationListener() {
        navigationListener = null
    }
}
