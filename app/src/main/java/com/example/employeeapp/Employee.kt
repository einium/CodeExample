package com.example.employeeapp

import java.text.SimpleDateFormat
import java.util.*

data class Employee(val f_name: String,
               val l_name: String,
               val birthday: String,
               val avatr_url: String,
               val specialty: Specialty) {
    private val defaultDate = "-"

    fun getAge(): String {
        val birthDate = parseStringDate(birthday)
        if (birthDate != null) {
            val ageInMillis = Date().time - birthDate.time
            return Date(ageInMillis).year.toString()
        }
        return defaultDate
    }

    fun getBirthDay(): String{
        val birthDate = parseStringDate(birthday)
        if (birthDate != null) {
            return getDateInHumanFormat(birthDate)
        }
        return defaultDate
    }
    private fun getDateInHumanFormat(date: Date): String {
        val humanFormatter = SimpleDateFormat("dd MM yyyy", Locale.getDefault())
        return humanFormatter.format(date.time)
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

    fun getLastName(): String {
        return l_name.capitalize()
    }

}

data class Specialty(val specialty_id: Int,
                val name: String)
