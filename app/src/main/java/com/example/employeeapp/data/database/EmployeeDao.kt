package com.example.employeeapp.data.database

import androidx.room.*
@Dao
interface EmployeeDao {
    @Query("Select * from employeeentity")
    fun getAll() : List<EmployeeEntity>

    @Query("Select * from employeeentity where id = :id")
    fun getById(id: Long): EmployeeEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(employee: EmployeeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg employee: EmployeeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(employee: List<EmployeeEntity>)

    @Update
    fun update(employee: EmployeeEntity)

    @Delete
    fun delete(employee: EmployeeEntity)
}