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
import com.example.employeeapp.DataViewModel
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.employeeapp.NavigationViewModel
import com.example.employeeapp.R
import com.example.employeeapp.data.model.Specialty
import com.example.employeeapp.adapters.OnSpecialtyClickCallback
import com.example.employeeapp.databinding.FragmentSpecialityListBinding

class SpecialityListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var listAdapter: SpecialtyListAdapter

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val binding: FragmentSpecialityListBinding =
            DataBindingUtil.inflate(inflater,R.layout.fragment_speciality_list, container,false)
        recyclerView = binding.specialityListRv
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAdapter = SpecialtyListAdapter(object : OnSpecialtyClickCallback {
            override fun onClick(specialty: Specialty) {
                dataViewModel.setNewSpecialty(specialty)
                navViewModel.onSpecialtyItemClick()
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

        val liveData = dataViewModel.getSpecialtyListLiveData()
        listAdapter.specialtyList = liveData.value
        liveData.observe(this, Observer {
                val oldList = listAdapter.specialtyList
                val newList = it
                val diffUtilCallback = SpecialtyDiffUtilCallback(oldList, newList)
                val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
                listAdapter.specialtyList = newList
                diffResult.dispatchUpdatesTo(listAdapter)
            })
    }
}

class SpecialtyDiffUtilCallback(private val oldList: List<Specialty>?, private val newList: List<Specialty>?) :
    DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        if (oldList == null || newList == null) return false

        val oldSpecialty = oldList[oldItemPosition]
        val newSpecialty = newList[newItemPosition]
        return oldSpecialty.specialty_id == newSpecialty.specialty_id
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

        val oldSpecialty = oldList[oldItemPosition]
        val newSpecialty = newList[newItemPosition]
        return oldSpecialty.name == newSpecialty.name
    }
}
