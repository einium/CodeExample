package com.example.employeeapp

import io.reactivex.Observable
import retrofit2.http.GET

interface RequestInterface {
    @GET("testTask.json")
    fun getEmployeeList(): Observable<EmployeeList>

}