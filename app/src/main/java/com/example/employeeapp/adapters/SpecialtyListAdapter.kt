package com.example.employeeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.employeeapp.MainViewModel
import com.example.employeeapp.R
import com.example.employeeapp.Specialty

class SpecialtyListAdapter(private val viewModel: MainViewModel) : RecyclerView.Adapter<SpecialtyListAdapter.SpecialtyViewHolder>() {
    private val list = viewModel.getSpecialtyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialtyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SpecialtyViewHolder(inflater, parent, viewModel)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SpecialtyViewHolder, position: Int) {
        val specialty: Specialty = list[position]
        holder.bind(specialty)
    }

    class SpecialtyViewHolder(inflater: LayoutInflater, parent: ViewGroup, val viewModel: MainViewModel):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.specialty_item, parent, false)) {

        private var nameTextView: TextView? = null
        init {
            nameTextView = itemView.findViewById(R.id.specialty_name)
        }

        fun bind(specialty: Specialty) {
            nameTextView?.text = specialty.name
            itemView.setOnClickListener { viewModel.onSpecialtyItemClick(specialty)}
        }
    }

}