package com.example.employeeapp

import android.content.Context
import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.IoScheduler
import io.reactivex.schedulers.Schedulers
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory


class Repository(context: Context) {
    private val dbHelper = DataBaseHelper(context)

    fun loadData(callback: LoadCallback){
        loadFromDB(object : LoadCallback {
            override fun onEmployeesLoaded(employeeList: List<Employee>) {
                callback.onEmployeesLoaded(employeeList)
            }
        })

        loadFromServer(object : LoadCallback {
            override fun onEmployeesLoaded(employeeList: List<Employee>) {
                callback.onEmployeesLoaded(employeeList)
            }
        })
    }

    fun loadFromServer(callback: LoadCallback) {
        Log.d("logTag", "Repository loadData")
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
                Log.d("logTag", "Repository loaded from server size: ${it.response.size}")
                callback.onEmployeesLoaded(it.response)
                saveToDB(it.response)
            },{
                val error = it.message
                Log.d("logTag", "Repository response.error: $error")
            })
    }

    fun loadFromDB(callback: LoadCallback){
        Observable.fromCallable {
            dbHelper.loadEmployees()
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.computation())
            .subscribe {
                Log.d("logTag", "Repository loaded from db size: ${it.size}")
                callback.onEmployeesLoaded(it)}
    }

    fun saveToDB(list: List<Employee>){
        Observable.fromCallable {
            dbHelper.saveEmployeeList(list)
        }.subscribeOn(Schedulers.computation())
            .subscribe()
    }

}
interface LoadCallback{
    fun onEmployeesLoaded(employeeList: List<Employee>)
}