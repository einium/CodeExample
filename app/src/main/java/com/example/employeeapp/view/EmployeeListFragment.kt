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
import com.example.employeeapp.MainViewModel
import com.example.employeeapp.R
import com.example.employeeapp.adapters.EmployeeListAdapter

class EmployeeListFragment: Fragment() {
    private var recyclerView: RecyclerView? = null
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
        recyclerView = view.findViewById(R.id.employee_list)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listLayoutManager = LinearLayoutManager(activity)
        recyclerView?.apply {
            layoutManager = listLayoutManager
            adapter = EmployeeListAdapter(viewModel)
        }
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView?.context,
            listLayoutManager.orientation
        )
        recyclerView?.addItemDecoration(dividerItemDecoration)
    }
}
