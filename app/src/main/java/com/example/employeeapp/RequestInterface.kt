package com.example.employeeapp

import io.reactivex.Observable
import retrofit2.http.GET

interface RequestInterface {
    @GET("65gb/static/raw/master/testTask.json")
    fun getEmployeeList(): Observable<List<Employee>>

}