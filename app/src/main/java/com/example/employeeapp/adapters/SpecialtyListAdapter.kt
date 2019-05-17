package com.example.employeeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.employeeapp.R
import com.example.employeeapp.Specialty

class SpecialtyListAdapter(private val list: List<Specialty>) : RecyclerView.Adapter<SpecialtyListAdapter.SpecialtyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialtyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SpecialtyViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SpecialtyViewHolder, position: Int) {
        val specialty: Specialty = list[position]
        holder.bind(specialty)
    }

    class SpecialtyViewHolder(inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.specialty_item, parent, false)) {

        fun bind(specialty: Specialty) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
}