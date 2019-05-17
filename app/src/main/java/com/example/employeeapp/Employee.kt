package com.example.employeeapp

data class Employee(val f_name: String,
               val l_name: String,
               val birthday: String,
               val avatr_url: String,
               val specialty: Specialty)

data class Specialty(val specialty_id: Int,
                val name: String)
