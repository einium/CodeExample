package com.example.employeeapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.employeeapp.Employee
import com.example.employeeapp.MainViewModel
import com.example.employeeapp.R

class EmployeeFragment : Fragment() {
    private var avatar: ImageView? = null
    private var name: TextView? = null
    private var birthDay: TextView? = null
    private var age: TextView? = null
    private var specialty: TextView? = null
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
        val view = inflater.inflate(R.layout.fragment_employee, container, false)
        bindViews(view)
        return view
    }

    private fun bindViews(view: View?) {
        avatar = view?.findViewById(R.id.empl_avatar)
        name = view?.findViewById(R.id.empl_name_value)
        birthDay = view?.findViewById(R.id.empl_birthday_value)
        age = view?.findViewById(R.id.empl_age_value)
        specialty = view?.findViewById(R.id.empl_specialty_value)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val employee = viewModel.getCurrentEmployee()
        if (employee != null) {
            setEmployeeData(employee)
        }
    }

    private fun setEmployeeData(employee: Employee) {
        setAvatar(employee.getAvatarUrl())
        val fullName = "${employee.getFirstName()} ${employee.getLastName()}"
        name?.text = fullName
        birthDay?.text = employee.getBirthDay()
        age?.text = employee.getAge()
        specialty?.text = employee.getSpecialtyName()
    }

    private fun setAvatar(url: String) {

        if (url != "") {
            Glide.with(context)
                .load(url)
                .transition(DrawableTransitionOptions().crossFade())
                .apply(RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                .into(avatar)
        } else {
            Glide.with(context)
                .load(R.drawable.employee)
                .into(avatar)
        }
    }
}
