package com.example.employeeapp.data.model

import java.util.*

data class EmployeeList(val response: List<Employee>)

data class Employee(
    val f_name: String,
    val l_name: String,
    val birthday: String?,
    val avatr_url: String?,
    val specialty: List<Specialty>
) {
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0

    fun getAge(): String {
        parseStringDate(birthday)
        if (year == 0)
            return "—"

        val dateOfBirth = Calendar.getInstance()
        dateOfBirth.set(year, month, day)
        val today = Calendar.getInstance()
        var age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR)
        if (today.get(Calendar.DAY_OF_YEAR) < dateOfBirth.get(Calendar.DAY_OF_YEAR)) {
            age--
        }
        return age.toString()
    }

    fun getBirthDay(): String {
        parseStringDate(birthday)
        return getDateInHumanFormat()
    }

    private fun getDateInHumanFormat(): String {
        if (year != 0)
            return "$day.$month.$year"
        return "———"
    }

    private fun parseStringDate(textDate: String?) {
        if (textDate == null) return

        val splitText = textDate.split('-')

        if (splitText.size > 2) {
            if (splitText[0].length > 2) {
                year = Integer.valueOf(splitText[0])
                month = Integer.valueOf(splitText[1])
                day = Integer.valueOf(splitText[2])
            } else {
                year = Integer.valueOf(splitText[2])
                month = Integer.valueOf(splitText[1])
                day = Integer.valueOf(splitText[0])
            }
        }
    }

    fun getFirstName(): String {
        return f_name.beginWithUpperCase()
    }

    fun getLastName(): String {
        return l_name.beginWithUpperCase()
    }

    fun getFullName(): String {
        return "${getFirstName()} ${getLastName()}"
    }

    private fun String.beginWithUpperCase(): String {
        return when (this.length) {
            0 -> ""
            1 -> this.toUpperCase()
            else -> this[0].toUpperCase() + this.substring(1).toLowerCase()
        }
    }

    fun getSpecialtyId(): Int {
        if (specialty.isNotEmpty()) {
            return specialty[0].specialty_id
        }
        return -1
    }

    fun getSpecialtyName(): String {
        if (specialty.isNotEmpty()) {
            return specialty[0].name
        }
        return ""
    }

    fun getAvatarUrl(): String {
        if (avatr_url != null) return avatr_url
        return ""
    }
}

data class Specialty(
    val specialty_id: Int,
    val name: String
)