package com.example.employeeapp

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

    init{
        currentFragment.postValue(FragmentName.SpecialityListFragment)
    }

    fun loadFromApi() {
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
                employeeListLiveData.postValue(it)
            },{
                val error = it.message
            })
    }

    fun getSpecialtyList() : List<Specialty>{
        val employeeList = employeeListLiveData.value
        val specialtySet = LinkedHashSet<Specialty>()
        if (employeeList != null) {
            for (employee in employeeList) {
                specialtySet.add(employee.specialty)
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
                if (employee.specialty.specialty_id == specialty.specialty_id) {
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