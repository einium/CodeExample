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
import androidx.recyclerview.widget.DiffUtil
import com.example.employeeapp.MainViewModel
import androidx.recyclerview.widget.DividerItemDecoration
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.example.employeeapp.R
import com.example.employeeapp.Specialty
import com.example.employeeapp.adapters.OnSpecialtyClickCallback


class SpecialityListFragment : Fragment() {
    @BindView(R.id.speciality_list_rv)
    lateinit var recyclerView: RecyclerView
    private lateinit var unbinder: Unbinder
    private lateinit var listAdapter: SpecialtyListAdapter
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
        unbinder = ButterKnife.bind(activity!!, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAdapter = SpecialtyListAdapter(viewModel.getSpecialtyList(), object : OnSpecialtyClickCallback {
            override fun onClick(specialty: Specialty) {
                viewModel.onSpecialtyItemClick(specialty)
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
        viewModel.getEmployeeListLiveData()
            .observe(this, Observer {
                val oldList = listAdapter.specialtyList
                val newList = viewModel.getSpecialtyList()
                val diffUtilCallback = DiffUtilCallback(oldList, newList)
                val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
                listAdapter.specialtyList = newList
                diffResult.dispatchUpdatesTo(listAdapter)
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()
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
