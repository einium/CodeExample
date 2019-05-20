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
import com.example.employeeapp.*
import com.example.employeeapp.data.EmployeeRepository
import com.example.employeeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var progressBar: ProgressBar

    private lateinit var viewModel : MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        progressBar = binding.progressBar

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.loadData(EmployeeRepository(this))

        setFragmentChangeObserver()
        setLoadingObserver()
        setErrorObserver()
    }

    private fun setFragmentChangeObserver(){
        viewModel.getCurrentFragmentLiveData()
            .observe(this, Observer {fragment -> setFragment(fragment)})

    }

    private fun setLoadingObserver(){
        viewModel.getLoadingLiveData()
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
        viewModel.getErrorLiveData()
            .observe(this, Observer {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            })
    }

    //todo inject by di
    private val specialityListFragment = SpecialityListFragment()
    private val employeeListFragment = EmployeeListFragment()
    private val employeeFragment = EmployeeFragment()

    private fun setFragment(fragmentName: FragmentName?){
        if (fragmentName == null) return

        val fragment = when (fragmentName) {
            FragmentName.SpecialityListFragment -> specialityListFragment
            FragmentName.EmployeeListFragment -> employeeListFragment
            FragmentName.EmployeeFragment -> employeeFragment
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.addToBackStack(fragmentName.name)
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}
