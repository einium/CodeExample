package com.example.employeeapp

import java.util.*

data class Employee(val f_name: String,
               val l_name: String,
               val birthday: String,
               val avatr_url: String,
               val specialty: Specialty) {

    fun getAge(): String {
        val birthDate = parseStringDate(birthday)
        if (birthDate != null) {
            val ageInMillis = Date().time - birthDate.time
            return Date(ageInMillis).year.toString()
        }
        return "-- -- ----"
    }

    private fun parseStringDate(textDate: String): Date? {
        val splitText = textDate.split('-')
        if (splitText.size > 2) {
            return if (splitText[0].length == 4) {
                Date(Integer.valueOf(splitText[0]), Integer.valueOf(splitText[1]), Integer.valueOf(splitText[2]))
            } else
                Date(Integer.valueOf(splitText[2]), Integer.valueOf(splitText[1]), Integer.valueOf(splitText[0]))
        }
        return null
    }

    fun getFirstName(): String {
        return f_name.capitalize()
    }

    fun getSecondName(): String {
        return l_name.capitalize()
    }

}

data class Specialty(val specialty_id: Int,
                val name: String)
