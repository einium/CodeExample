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
import retrofit2.http.GET

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

    private fun loadFromServer(callback: LoadCallback) {
        val retrofit = createRetrofit()
        val request = retrofit.create(RequestInterface::class.java)
        val response = request.getEmployeeList()
        response.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(IoScheduler())
            .subscribe({
                callback.onEmployeesLoaded(it.response)
                saveToDB(it.response)
            },{
                val error = it.message
                Log.d("logTag", "Repository response.error: $error")
            })
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://gitlab.65apps.com/")
            .build()
    }

    private fun loadFromDB(callback: LoadCallback){
        Observable.fromCallable {
            dbHelper.loadEmployees()
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.computation())
            .subscribe {
                callback.onEmployeesLoaded(it)}
    }

    private fun saveToDB(list: List<Employee>){
        Observable.fromCallable {
            dbHelper.saveEmployeeList(list)
        }.subscribeOn(Schedulers.computation())
            .subscribe()
    }

}
interface LoadCallback{
    fun onEmployeesLoaded(employeeList: List<Employee>)
}
interface RequestInterface {
    @GET("65gb/static/raw/master/testTask.json")
    fun getEmployeeList(): Observable<EmployeeList>
}