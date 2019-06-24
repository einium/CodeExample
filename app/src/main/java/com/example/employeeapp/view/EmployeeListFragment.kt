package com.example.employeeapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.employeeapp.data.model.Employee
import com.example.employeeapp.DataViewModel
import com.example.employeeapp.NavigationViewModel
import com.example.employeeapp.R
import com.example.employeeapp.adapters.EmployeeListAdapter
import com.example.employeeapp.adapters.OnEmployeeClickCallback
import com.example.employeeapp.data.model.Specialty
import com.example.employeeapp.databinding.FragmentEmployeeListBinding

class EmployeeListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var listAdapter: EmployeeListAdapter

    private lateinit var dataViewModel: DataViewModel
    private lateinit var navViewModel: NavigationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataViewModel = activity?.run {
            ViewModelProviders.of(this).get(DataViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        navViewModel = activity?.run {
            ViewModelProviders.of(this).get(NavigationViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentEmployeeListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_employee_list, container, false)

        recyclerView = binding.employeeList
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAdapter = EmployeeListAdapter(object : OnEmployeeClickCallback {
            override fun onClick(employee: Employee) {
                dataViewModel.setNewEmployee(employee)
                navViewModel.onEmployeeItemClick()
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

        val liveData = dataViewModel.getEmployeeBySpecialtyListLiveData()
        listAdapter.employeeList = liveData.value
        liveData.observe(this, Observer {
            val oldList = listAdapter.employeeList
            val newList = it
            val diffUtilCallback = EmployeeDiffUtilCallback(oldList, newList)
            val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
            listAdapter.employeeList = newList
            diffResult.dispatchUpdatesTo(listAdapter)
        })
    }
}

class EmployeeDiffUtilCallback(private val oldList: List<Employee>?, private val newList: List<Employee>?) :
    DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        if (oldList == null || newList == null) return false

        val oldEmployee = oldList[oldItemPosition]
        val newEmployee = newList[newItemPosition]
        return oldEmployee == newEmployee
    }

    override fun getOldListSize(): Int {
        if (oldList == null) return 0
        return oldList.size
    }

    override fun getNewListSize(): Int {
        if (newList == null) return 0
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        if (oldList == null || newList == null) return false

        val oldEmployee = oldList[oldItemPosition]
        val newEmployee = newList[newItemPosition]
        val firstNames = oldEmployee.f_name == newEmployee.f_name
        val lastNames = oldEmployee.l_name == newEmployee.l_name
        val birthDays = oldEmployee.birthday == newEmployee.birthday
        val specs = oldEmployee.getSpecialtyId() == newEmployee.getSpecialtyId()
        return firstNames && lastNames && birthDays && specs
    }
}