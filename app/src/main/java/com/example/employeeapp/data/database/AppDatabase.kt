package com.example.employeeapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [EmployeeEntity::class], version = 1)
public abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao() : EmployeeDao
}