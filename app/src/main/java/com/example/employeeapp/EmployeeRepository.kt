package com.example.employeeapp

import android.content.Context
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.IoScheduler
import io.reactivex.schedulers.Schedulers
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.GET

class EmployeeRepository(context: Context) : Repository {
    private val dbHelper = DataBaseHelper(context)
    private val retrofit = Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://gitlab.65apps.com/")
        .build()

    override fun loadData(callback: LoadCallback) {
        loadFromDB(object : LoadCallback {
            override fun startLoad() {

            }

            override fun onEmployeesLoaded(employeeList: List<Employee>) {
                callback.onEmployeesLoaded(employeeList)
            }

            override fun onError(message: String) {
                callback.onError(message)
            }
        })

        loadFromServer(object : LoadCallback {
            override fun startLoad() {

            }

            override fun onEmployeesLoaded(employeeList: List<Employee>) {
                callback.onEmployeesLoaded(employeeList)
            }

            override fun onError(message: String) {
                callback.onError(message)
            }
        })
    }

    private fun loadFromServer(callback: LoadCallback) {
        callback.startLoad()
        val request = retrofit.create(RequestInterface::class.java)
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

interface RequestInterface {
    @GET("65gb/static/raw/master/testTask.json")
    fun getEmployeeList(): Observable<EmployeeList>
}