package com.example.employeeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.employeeapp.Employee
import com.example.employeeapp.R
import com.example.employeeapp.databinding.EmployeeItemBinding

class EmployeeListAdapter(private val employeeList: List<Employee>, private val clickCallback: OnEmployeeClickCallback) :
    RecyclerView.Adapter<EmployeeListAdapter.SpecialtyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialtyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: EmployeeItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.employee_item, parent, false)
        return SpecialtyViewHolder(binding, clickCallback)
    }

    override fun getItemCount(): Int {
        return employeeList.size
    }

    override fun onBindViewHolder(holder: SpecialtyViewHolder, position: Int) {
        val employee: Employee = employeeList[position]
        holder.bind(employee)
    }

    class SpecialtyViewHolder(private val binding: EmployeeItemBinding,
                              private val clickCallback: OnEmployeeClickCallback):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(employee: Employee) {
            binding.employee = employee
            binding.employeeItemContent.setOnClickListener { clickCallback.onClick(employee)}
        }
    }
}

interface OnEmployeeClickCallback{
    fun onClick(employee: Employee)
}