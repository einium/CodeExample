package com.example.employeeapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.employeeapp.Employee
import com.example.employeeapp.MainViewModel
import com.example.employeeapp.R
import com.example.employeeapp.adapters.EmployeeListAdapter
import com.example.employeeapp.adapters.OnEmployeeClickCallback
import com.example.employeeapp.databinding.FragmentEmployeeListBinding

class EmployeeListFragment: Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var listAdapter: EmployeeListAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentEmployeeListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_employee_list, container,false)

        recyclerView = binding.employeeList
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAdapter = EmployeeListAdapter(viewModel.getEmployeeList(), object: OnEmployeeClickCallback {
            override fun onClick(employee: Employee) {
                viewModel.onEmployeeItemClick(employee)
            }
        })
        val listLayoutManager = LinearLayoutManager(activity)
        recyclerView.apply {
            layoutManager = listLayoutManager
            adapter = listAdapter
        }
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            listLayoutManager.orientation
        )
        recyclerView.addItemDecoration(dividerItemDecoration)
    }
}
