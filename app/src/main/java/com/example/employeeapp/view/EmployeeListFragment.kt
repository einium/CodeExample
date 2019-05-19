package com.example.employeeapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.example.employeeapp.Employee
import com.example.employeeapp.MainViewModel
import com.example.employeeapp.R
import com.example.employeeapp.adapters.EmployeeListAdapter
import com.example.employeeapp.adapters.OnEmployeeClickCallback

class EmployeeListFragment: Fragment() {
    @BindView(R.id.employee_list) lateinit var recyclerView: RecyclerView
    private lateinit var unbinder: Unbinder
    private lateinit var listAdapter: EmployeeListAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_employee_list, container, false)
        unbinder = ButterKnife.bind(activity!!, view)
        return view
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

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()
    }
}
