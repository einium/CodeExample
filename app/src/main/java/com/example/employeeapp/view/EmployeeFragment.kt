package com.example.employeeapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.employeeapp.Employee
import com.example.employeeapp.MainViewModel
import com.example.employeeapp.R

class EmployeeFragment : Fragment() {
    @BindView(R.id.empl_avatar) lateinit var avatar: ImageView
    @BindView(R.id.empl_name_value) lateinit var name: TextView
    @BindView(R.id.empl_birthday_value) lateinit var birthDay: TextView
    @BindView(R.id.empl_age_value) lateinit var age: TextView
    @BindView(R.id.empl_specialty_value) lateinit var specialty: TextView
    private lateinit var viewModel: MainViewModel
    private lateinit var unbinder: Unbinder

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
        unbinder = ButterKnife.bind(activity!!, view)
        return view
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
        name.text = employee.getFullName()
        birthDay.text = employee.getBirthDay()
        age.text = employee.getAge()
        specialty.text = employee.getSpecialtyName()
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

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()
    }
}
