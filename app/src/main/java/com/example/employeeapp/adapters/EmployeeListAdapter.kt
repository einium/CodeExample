package com.example.employeeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.employeeapp.Employee
import com.example.employeeapp.MainViewModel
import com.example.employeeapp.R

class EmployeeListAdapter(private val viewModel: MainViewModel) : RecyclerView.Adapter<EmployeeListAdapter.SpecialtyViewHolder>() {
    private val list = viewModel.getEmployeeList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialtyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SpecialtyViewHolder(inflater, parent, viewModel)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SpecialtyViewHolder, position: Int) {
        val employee: Employee = list[position]
        holder.bind(employee)
    }

    class SpecialtyViewHolder(inflater: LayoutInflater, parent: ViewGroup, private val viewModel: MainViewModel):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.employee_item, parent, false)) {

        private var firstNameTextView: TextView? = null
        private var lastNameTextView: TextView? = null
        private var ageTextView: TextView? = null
        init {
            firstNameTextView = itemView.findViewById(R.id.employee_first_name)
            lastNameTextView = itemView.findViewById(R.id.employee_last_name)
            ageTextView = itemView.findViewById(R.id.employee_age)
        }

        fun bind(employee: Employee) {
            firstNameTextView?.text = employee.getFirstName()
            lastNameTextView?.text = employee.getLastName()
            ageTextView?.text = employee.getAge()
            itemView.setOnClickListener { viewModel.onEmployeeItemClick(employee)}
        }
    }

}