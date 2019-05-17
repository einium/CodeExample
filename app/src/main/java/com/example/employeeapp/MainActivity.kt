package com.example.employeeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity() {
    private var viewModel : MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        viewModel?.currentFragment?.observe(this, Observer { fragment -> setFragment(fragment)})
        viewModel?.loadFromApi()
    }

    private fun setFragment(fragmentName: FragmentName?){
        if (fragmentName == null) return

        val fragment = when (fragmentName) {
            FragmentName.SpecialityListFragment -> SpecialityListFragment()
            FragmentName.EmployeeListFragment -> EmployeeListFragment()
            FragmentName.EmployeeFragment -> EmployeeFragment()
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}
