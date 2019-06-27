package com.example.employeeapp.data.database

import androidx.room.*

@Entity
class EmployeeEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int = 0
    @ColumnInfo(name = "fName") var fName: String = ""
    @ColumnInfo(name = "lName") var lName: String = ""
    @ColumnInfo(name = "birthday") var birthday: String = ""
    @ColumnInfo(name = "avatarUrl") var avatarUrl: String = ""
    @ColumnInfo(name = "specialtyId") var specialtyId: Int = 0
    @ColumnInfo(name = "specialtyName") var specialtyName: String = ""
}