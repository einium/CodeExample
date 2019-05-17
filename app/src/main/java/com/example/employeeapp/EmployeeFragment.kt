package com.example.employeeapp

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

class EmployeeFragment : Fragment() {
    private var viewModel: MainViewModel? = null
    private var avatar: ImageView? = null
    private var name: TextView? = null
    private var birthDay: TextView? = null
    private var specialty: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
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
        name = view?.findViewById(R.id.empl_name)
        birthDay = view?.findViewById(R.id.empl_birthDay)
        specialty = view?.findViewById(R.id.empl_specialty)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val employee = viewModel?.getCurrentEmployee()
        if (employee != null) {
            setEmployeeData(employee)
        }
    }

    private fun setEmployeeData(employee: Employee) {
        setAvatar(employee.avatr_url)
        val fullName = "${employee.getFirstName()} ${employee.getLastName()}"
        name?.text = fullName
        val birthAndAge = "Birthday: ${employee.getBirthDay()} (${employee.getAge()} years)"
        birthDay?.text = birthAndAge
        val spec = "Specialty: ${employee.specialty.name}"
        specialty?.text = spec
    }

    private fun setAvatar(url: String) {
        Glide.with(context)
            .load(url)
            .transition(
                DrawableTransitionOptions()
                    .crossFade()
            )
            .apply(
                RequestOptions()
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .skipMemoryCache(true)
            )
            .into(avatar)
    }
}
