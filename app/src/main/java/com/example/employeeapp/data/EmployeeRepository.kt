package com.example.employeeapp.data

import com.example.employeeapp.App
import com.example.employeeapp.data.model.Employee
import com.example.employeeapp.data.model.EmployeeList
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.IoScheduler
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.http.GET
import javax.inject.Inject

class EmployeeRepository @Inject constructor() : Repository {
    init {
        App.getAppComponent().inject(this)
    }

    @Inject
    lateinit var dbHelper: DataBaseHelper

    @Inject
    lateinit var retrofit: Retrofit

    override fun loadData(callback: LoadCallback) {
        //loadFromDB(callback)
        loadFromServer(callback)
    }

    private fun loadFromServer(callback: LoadCallback) {
        callback.startLoad()
        val request = retrofit.create(EmployeeService::class.java)
        val response = request.getEmployeeList()
        response.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(IoScheduler())
            .subscribe({
                callback.onEmployeesLoaded(it.response)
                saveToDB(it.response)
            }, {
                callback.onError(it.message!!)
            })
    }

    private fun loadFromDB(callback: LoadCallback) {
        callback.startLoad()
        Observable.fromCallable {
            dbHelper.loadEmployees()
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.computation())
            .subscribe({
                callback.onEmployeesLoaded(it)
            },
                {
                    callback.onError(it.message!!)
                })
    }

    private fun saveToDB(list: List<Employee>) {
        Observable.fromCallable {
            dbHelper.saveEmployeeList(list)
        }.subscribeOn(Schedulers.computation())
            .subscribe()
    }
}

interface Repository {
    fun loadData(callback: LoadCallback)
}

interface LoadCallback {
    fun startLoad()
    fun onEmployeesLoaded(employeeList: List<Employee>)
    fun onError(message: String)
}

interface EmployeeService {
    @GET("65gb/static/raw/master/testTask.json")
    fun getEmployeeList(): Observable<EmployeeList>
}