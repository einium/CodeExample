package com.example.employeeapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
//import butterknife.BindView
//import butterknife.ButterKnife
//import butterknife.Unbinder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.employeeapp.Employee
import com.example.employeeapp.MainViewModel
import com.example.employeeapp.R
import com.example.employeeapp.databinding.FragmentEmployeeBinding

class EmployeeFragment : Fragment() {
    private lateinit var avatar: ImageView
    private lateinit var name: TextView
    private lateinit var birthDay: TextView
    private lateinit var age: TextView
    private lateinit var specialty: TextView
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentEmployeeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee, container, false)

        avatar = binding.emplAvatar
        name = binding.emplNameValue
        birthDay = binding.emplBirthdayValue
        age = binding.emplAgeValue
        specialty = binding.emplSpecialtyValue
        return binding.root
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
}
