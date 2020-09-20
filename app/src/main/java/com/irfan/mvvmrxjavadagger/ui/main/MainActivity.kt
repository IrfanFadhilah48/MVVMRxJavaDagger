package com.irfan.mvvmrxjavadagger.ui.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.irfan.mvvmrxjavadagger.R
import com.irfan.mvvmrxjavadagger.common.ResultStatus
import com.irfan.mvvmrxjavadagger.ui.main.viewmodel.MainViewModel
import com.irfan.mvvmrxjavadagger.util.SharedPreferenceHelper
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(){

    private lateinit var viewmodel: MainViewModel
    private lateinit var navView: BottomNavigationView

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var mSharedPref: SharedPreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidInjection.inject(this)

        viewmodel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewmodel.dataGenre.observe(this, Observer {
            it?.let {resultState ->
                when(resultState.status){
                    ResultStatus.LOADING ->{
                        Log.e("datanyaMain", "LOADING")
                    }
                    ResultStatus.SUCCESS ->{
                        Log.e("datanyaMain", resultState.data?.size.toString())
                        mSharedPref.putDataGenre(resultState.data)
                        setNavigation()
                    }
                    ResultStatus.ERROR -> {
                        Log.e("datanyaMain", "ERROR")
                    }
                }
            }
        })

        if (mSharedPref.getDataGenre() == ""){
            viewmodel.callGetGenre("en-US")
        }
        else{
            setNavigation()
        }
    }

    private fun setNavigation(){
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home,
            R.id.navigation_dashboard,
            R.id.navigation_notifications
        ))
        setupActionBarWithNavController(navController, appBarConfiguration)
        nav_view.setupWithNavController(navController)
        cnb_main.setItemSelected(R.id.navigation_home)
        cnb_main.setOnItemSelectedListener {itemId ->
            nav_view.selectedItemId = itemId
        }
    }
}