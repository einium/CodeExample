package com.example.employeeapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.employeeapp.*

class MainActivity : AppCompatActivity() {
    private var viewModel : MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel?.loadData(Repository(this))
        viewModel?.currentFragment?.observe(this, Observer {
                fragment -> setFragment(fragment)})
    }

    private fun setFragment(fragmentName: FragmentName?){
        if (fragmentName == null) return

        val fragment = when (fragmentName) {
            FragmentName.SpecialityListFragment -> SpecialityListFragment()
            FragmentName.EmployeeListFragment -> EmployeeListFragment()
            FragmentName.EmployeeFragment -> EmployeeFragment()
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.addToBackStack(fragmentName.name)
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

}
