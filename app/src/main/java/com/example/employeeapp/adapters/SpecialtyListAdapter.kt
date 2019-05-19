package com.example.employeeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.example.employeeapp.MainViewModel
import com.example.employeeapp.R
import com.example.employeeapp.Specialty

class SpecialtyListAdapter(var specialtyList: List<Specialty>, private val callback: OnSpecialtyClickCallback) :
    RecyclerView.Adapter<SpecialtyListAdapter.SpecialtyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialtyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SpecialtyViewHolder(inflater, parent, callback)
    }

    override fun getItemCount(): Int {
        return specialtyList.size
    }

    override fun onBindViewHolder(holder: SpecialtyViewHolder, position: Int) {
        val specialty: Specialty = specialtyList[position]
        holder.bind(specialty)
    }

    class SpecialtyViewHolder(inflater: LayoutInflater, parent: ViewGroup, private val callback: OnSpecialtyClickCallback):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.specialty_item, parent, false)) {

        @BindView(R.id.specialty_name) lateinit var specialty: TextView

        fun bind(specialty: Specialty) {
            this.specialty.text = specialty.name
            itemView.setOnClickListener {
                callback.onClick(specialty)
            }
        }
    }
}

interface OnSpecialtyClickCallback{
    fun onClick(specialty: Specialty)
}