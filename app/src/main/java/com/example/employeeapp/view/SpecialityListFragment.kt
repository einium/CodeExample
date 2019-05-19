package com.example.employeeapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.employeeapp.adapters.SpecialtyListAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.employeeapp.MainViewModel
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.employeeapp.R

class SpecialityListFragment: Fragment() {
    private var recyclerView: RecyclerView? = null
    private var listAdapter: SpecialtyListAdapter? = null
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
        val view = inflater.inflate(R.layout.fragment_speciality_list, container, false)
        recyclerView = view.findViewById(R.id.speciality_list_rv)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAdapter = SpecialtyListAdapter(viewModel)
        val listLayoutManager = LinearLayoutManager(activity)
        recyclerView?.apply {
            layoutManager = listLayoutManager
            adapter = listAdapter
        }
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView?.context,
            listLayoutManager.orientation
        )
        recyclerView?.addItemDecoration(dividerItemDecoration)
        viewModel.employeeListLiveData.observe(this, Observer {
            listAdapter?.notifyDataSetChanged()
        })
    }
}
