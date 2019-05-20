package com.example.employeeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.employeeapp.R
import com.example.employeeapp.Specialty
import com.example.employeeapp.databinding.SpecialtyItemBinding

class SpecialtyListAdapter(var specialtyList: List<Specialty>, private val callback: OnSpecialtyClickCallback) :
    RecyclerView.Adapter<SpecialtyListAdapter.SpecialtyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialtyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: SpecialtyItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.specialty_item, parent, false)
        return SpecialtyViewHolder(binding, callback)
    }

    override fun getItemCount(): Int {
        return specialtyList.size
    }

    override fun onBindViewHolder(holder: SpecialtyViewHolder, position: Int) {
        val specialty: Specialty = specialtyList[position]
        holder.bind(specialty)
    }

    class SpecialtyViewHolder(private val binding: SpecialtyItemBinding, private val callback: OnSpecialtyClickCallback):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(specialty: Specialty) {
            binding.specialty = specialty
            binding.specialtyItemContent.setOnClickListener {
                callback.onClick(specialty)
            }
        }
    }
}

interface OnSpecialtyClickCallback{
    fun onClick(specialty: Specialty)
}