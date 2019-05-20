package com.example.employeeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
//import butterknife.BindView
import com.example.employeeapp.Employee
import com.example.employeeapp.R

class EmployeeListAdapter(val employeeList: List<Employee>, private val clickCallback: OnEmployeeClickCallback) : RecyclerView.Adapter<EmployeeListAdapter.SpecialtyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialtyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SpecialtyViewHolder(inflater, parent, clickCallback)
    }

    override fun getItemCount(): Int {
        return employeeList.size
    }

    override fun onBindViewHolder(holder: SpecialtyViewHolder, position: Int) {
        val employee: Employee = employeeList[position]
        holder.bind(employee)
    }

    class SpecialtyViewHolder(inflater: LayoutInflater, parent: ViewGroup, private val clickCallback: OnEmployeeClickCallback):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.employee_item, parent, false)) {
        //@BindView(R.id.employee_first_name)
        lateinit var firstNameTextView: TextView
        //@BindView(R.id.employee_last_name)
        lateinit var lastNameTextView: TextView
        //@BindView(R.id.employee_age_value)
        lateinit var ageTextView: TextView
        init {
            firstNameTextView = itemView.findViewById(R.id.employee_first_name)
            lastNameTextView = itemView.findViewById(R.id.employee_last_name)
            ageTextView = itemView.findViewById(R.id.employee_age_value)
        }

        fun bind(employee: Employee) {
            firstNameTextView.text = employee.getFirstName()
            lastNameTextView.text = employee.getLastName()
            ageTextView.text = employee.getAge()
            itemView.setOnClickListener { clickCallback.onClick(employee)}
        }
    }
}

interface OnEmployeeClickCallback{
    fun onClick(employee: Employee)
}