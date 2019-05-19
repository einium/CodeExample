package com.example.employeeapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.util.ArrayList

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    @Throws(SQLiteConstraintException::class)
    fun saveEmployeeList(employees: List<Employee>) {
        clearDb()
        for (employee in employees) {
            insertEmployee(employee)
        }
    }

    private fun clearDb() {
        val db = writableDatabase
        db.execSQL(SQL_DELETE_ENTRIES)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertEmployee(employee: Employee) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_F_NAME, employee.f_name)
        values.put(COLUMN_L_NAME, employee.l_name)
        values.put(COLUMN_BIRTHDAY, employee.birthday)
        values.put(COLUMN_AVATAR_URL, employee.avatr_url)
        values.put(COLUMN_SPECIALTY_ID, employee.getSpecialtyId())
        values.put(COLUMN_SPECIALTY_NAME, employee.getSpecialtyName())
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    @Throws(SQLiteConstraintException::class)
    fun loadEmployees(): List<Employee> {
        val db = writableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)
        val resultList = ArrayList<Employee>()
        if (cursor.moveToFirst()) {
            do {
                val fName = cursor.getString(cursor.getColumnIndex(COLUMN_F_NAME))
                val lName = cursor.getString(cursor.getColumnIndex(COLUMN_L_NAME))
                val birthDay = cursor.getString(cursor.getColumnIndex(COLUMN_BIRTHDAY))
                val avatarUrl = cursor.getString(cursor.getColumnIndex(COLUMN_AVATAR_URL))
                val specialtyId = cursor.getInt(cursor.getColumnIndex(COLUMN_SPECIALTY_ID))
                val specialtyName = cursor.getString(cursor.getColumnIndex(COLUMN_SPECIALTY_NAME))
                val specialty = Specialty(specialtyId, specialtyName)
                val employee = Employee(fName, lName, birthDay, avatarUrl, arrayListOf(specialty))
                resultList.add(employee)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return resultList
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "employers.db"
        const val TABLE_NAME = "employers"
        const val COLUMN_F_NAME = "f_name"
        const val COLUMN_L_NAME = "l_name"
        const val COLUMN_BIRTHDAY = "birthday"
        const val COLUMN_AVATAR_URL = "avatar_url"
        const val COLUMN_SPECIALTY_ID = "specialty_id"
        const val COLUMN_SPECIALTY_NAME = "specialty_name"

        private const val SQL_CREATE_ENTRIES =
            "create table " + TABLE_NAME + " (" +
                    "id integer primary key autoincrement," +
                    COLUMN_F_NAME + " text," +
                    COLUMN_L_NAME + " text," +
                    COLUMN_BIRTHDAY + " text," +
                    COLUMN_AVATAR_URL + " text," +
                    COLUMN_SPECIALTY_ID + " integer," +
                    COLUMN_SPECIALTY_NAME + " text)"

        private const val SQL_DELETE_ENTRIES = "delete from $TABLE_NAME"
    }

}
