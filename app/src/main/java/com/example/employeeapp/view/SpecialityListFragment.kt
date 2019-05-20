package com.example.employeeapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.employeeapp.adapters.SpecialtyListAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import com.example.employeeapp.MainViewModel
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.employeeapp.R
import com.example.employeeapp.Specialty
import com.example.employeeapp.adapters.OnSpecialtyClickCallback
import com.example.employeeapp.databinding.FragmentSpecialityListBinding

class SpecialityListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var listAdapter: SpecialtyListAdapter
    private var viewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val binding: FragmentSpecialityListBinding =
            DataBindingUtil.inflate(inflater,R.layout.fragment_speciality_list, container,false)
        recyclerView = binding.specialityListRv
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAdapter = SpecialtyListAdapter(viewModel?.getSpecialtyList()!!, object : OnSpecialtyClickCallback {
            override fun onClick(specialty: Specialty) {
                viewModel?.onSpecialtyItemClick(specialty)
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
        viewModel?.getEmployeeListLiveData()!!
            .observe(this, Observer {
                val oldList = listAdapter.specialtyList
                val newList = viewModel?.getSpecialtyList()
                val diffUtilCallback = DiffUtilCallback(oldList, newList!!)
                val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
                listAdapter.specialtyList = newList
                diffResult.dispatchUpdatesTo(listAdapter)
            })
    }
}

class DiffUtilCallback(private val oldList: List<Specialty>, private val newList: List<Specialty>) :
    DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldSpecialty = oldList[oldItemPosition]
        val newSpecialty = newList[newItemPosition]
        return oldSpecialty.specialty_id == newSpecialty.specialty_id
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldSpecialty = oldList[oldItemPosition]
        val newSpecialty = newList[newItemPosition]
        return oldSpecialty.name == newSpecialty.name
    }
}
