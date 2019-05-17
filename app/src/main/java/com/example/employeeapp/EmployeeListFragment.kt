package com.example.employeeapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.employeeapp.adapters.EmployeeListAdapter

class EmployeeListFragment : Fragment() {
    private var viewModel: MainViewModel? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val parentActivity = activity
        //if (parentActivity != null)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
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
        recyclerView?.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = EmployeeListAdapter(viewModel!!)
        }
    }
}
