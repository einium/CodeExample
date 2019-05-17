package com.example.employeeapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.IoScheduler
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {
    val employeeListLiveData = MutableLiveData<List<Employee>>()
    val currentFragment = MutableLiveData<FragmentName>()
    private var currentSpecialty: Specialty? = null
    private var currentEmployee: Employee? = null

    fun loadFromApi() {
        Log.d("logTag", "MainViewModel loadFromApi")
        val retrofit: Retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://gitlab.65apps.com/65gb/static/raw/master/")
            .build()
        val request = retrofit.create(RequestInterface::class.java)
        val response = request.getEmployeeList()
        response.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(IoScheduler())
            .subscribe({
                Log.d("logTag", "MainViewModel response.size: ${it.response.size}")
                employeeListLiveData.postValue(it.response)
                if (currentFragment.value == null){
                    currentFragment.postValue(FragmentName.SpecialityListFragment)
                }
            },{
                val error = it.message
                Log.d("logTag", "MainViewModel response.error: $error")
            })
    }

    fun getSpecialtyList() : List<Specialty>{
        Log.d("logTag", "MainViewModel getSpecialtyList")
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
        Log.d("logTag", "MainViewModel onSpecialtyItemClick specialty: ${specialty.name}")
        currentSpecialty = specialty
        currentFragment.postValue(FragmentName.EmployeeListFragment)
    }

    fun getEmployeeList(): List<Employee> {
        Log.d("logTag", "MainViewModel getEmployeeList")
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
        Log.d("logTag", "MainViewModel onEmployeeItemClick employee: ${employee.getFirstName()}")
        currentEmployee = employee
        currentFragment.postValue(FragmentName.EmployeeFragment)
    }

    fun getCurrentEmployee() : Employee? {
        Log.d("logTag", "MainViewModel getCurrentEmployee")
        return currentEmployee
    }
}
enum class FragmentName{
    SpecialityListFragment, EmployeeListFragment, EmployeeFragment
}