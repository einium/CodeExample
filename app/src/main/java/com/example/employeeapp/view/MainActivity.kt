package com.example.employeeapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.employeeapp.*
import com.example.employeeapp.data.model.Employee
import com.example.employeeapp.data.model.Specialty
import com.example.employeeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavigationListener {
    private lateinit var progressBar: ProgressBar

    private lateinit var dataViewModel : DataViewModel
    private lateinit var navViewModel : NavigationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navController = findNavController(R.id.nav_host_fragment)

        progressBar = binding.progressBar

        dataViewModel = ViewModelProviders.of(this).get(DataViewModel::class.java)
        dataViewModel.loadData()

        navViewModel = ViewModelProviders.of(this).get(NavigationViewModel::class.java)

        setLoadingObserver()
        setErrorObserver()
    }

    override fun onResume() {
        super.onResume()
        navViewModel.addNavigationListener(this)
    }

    override fun onPause() {
        super.onPause()
        navViewModel.removeNavigationListener()
    }

    private fun setLoadingObserver(){
        dataViewModel.getLoadingLiveData()
            .observe(this, Observer {isLoading ->
                if (isLoading) {
                    progressBar.visibility = View.VISIBLE
                    window.setFlags(
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                } else {
                    progressBar.visibility = View.GONE
                    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                }
            })
    }
    private fun setErrorObserver(){
        dataViewModel.getErrorLiveData()
            .observe(this, Observer {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            })
    }

    private lateinit var navController: NavController

    override fun navigateToEmployeeList() {
        navController.navigate(R.id.action_specialityListFragment_to_employeeListFragment)
    }

    override fun navigateToEmployee() {
        navController.navigate(R.id.action_employeeListFragment_to_employeeFragment)
    }
}

interface NavigationListener{
    fun navigateToEmployeeList()
    fun navigateToEmployee()
}
