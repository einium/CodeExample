package com.example.employeeapp.data

import com.example.employeeapp.App
import com.example.employeeapp.data.database.EmployeeDao
import com.example.employeeapp.data.database.EmployeeEntity
import com.example.employeeapp.data.model.Employee
import com.example.employeeapp.data.model.EmployeeList
import com.example.employeeapp.data.model.Specialty
import io.reactivex.Observable
import io.reactivex.Scheduler
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
    lateinit var employeeDao: EmployeeDao

    @Inject
    lateinit var retrofit: Retrofit

    override fun loadData(callback: LoadCallback) {
        loadFromDB(callback)
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
            employeeDao.getAll()
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.computation())
            .subscribe({
                val employeeList = convertToEmployee(it)
                callback.onEmployeesLoaded(employeeList)
            },
                {
                    callback.onError(it.message!!)
                })
    }

    private fun convertToEmployee(dbList: List<EmployeeEntity>): List<Employee> {
        val resultList = ArrayList<Employee>()
        for (entity in dbList){
            resultList.add(
                Employee(entity.fName,
                entity.lName,
                    entity.birthday,
                    entity.avatarUrl,
                    listOf(Specialty(entity.specialtyId, entity.specialtyName))))
        }
        return resultList
    }

    private fun saveToDB(list: List<Employee>) {
        val entityList = convertToEntity(list)
        Observable.fromCallable {
            employeeDao.insertAll(entityList)
        }.subscribeOn(Schedulers.computation())
            .subscribe()
    }

    private fun convertToEntity(list: List<Employee>): List<EmployeeEntity> {
        val resultList = ArrayList<EmployeeEntity>()
        for (employee in list){
            val entity = EmployeeEntity()
            entity.fName = employee.f_name
            entity.lName = employee.l_name
            entity.birthday = employee.birthday ?: ""
            entity.avatarUrl = employee.getAvatarUrl()
            entity.specialtyId = employee.getSpecialtyId()
            entity.specialtyName = employee.getSpecialtyName()
            resultList.add(entity)
        }
        return resultList
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