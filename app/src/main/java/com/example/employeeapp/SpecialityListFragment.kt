package com.example.employeeapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.example.employeeapp.adapters.SpecialtyListAdapter


class SpecialityListFragment : Fragment() {
    val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

    @BindView(R.id.speciality_list_rv)
    internal var recyclerView: RecyclerView? = null

    private var unbinder: Unbinder? = null
    private val list = ArrayList<Specialty>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_speciality_list, container, false)
        unbinder = ButterKnife.bind(this, view)
        updateList()
        return view
    }

    private fun updateList() {
        list.clear()
        list.addAll(viewModel.getSpecialtyList())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView?.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = SpecialtyListAdapter(list)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbinder?.unbind()
    }
}
