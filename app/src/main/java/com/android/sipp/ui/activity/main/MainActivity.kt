package com.android.sipp.ui.activity.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.sipp.R
import com.android.sipp.databinding.ActivityMainBinding
import com.android.sipp.model.Order
import com.android.sipp.preference.Constants
import com.android.sipp.preference.PreferenceManager
import com.android.sipp.ui.activity.PickupCategoryActivity
import com.android.sipp.ui.fragment.CartFragment
import com.android.sipp.ui.fragment.HistoryFragment
import com.android.sipp.ui.fragment.HomeFragment
import com.android.sipp.ui.fragment.ProfileFragment
import com.android.sipp.utils.Utils.showToast
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener,
    View.OnClickListener {

    private var order: Order = Order()

    private lateinit var preferenceManager: PreferenceManager
    private lateinit var mainViewModel: MainViewModel
    private lateinit var b: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)
        initiate()
        setListener()
        checkStatusPickup()
        home()
    }

    private fun initiate() {
        b.progressbar.visibility = VISIBLE
        preferenceManager = PreferenceManager(this)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private fun setListener() {
        b.bnv.setOnNavigationItemSelectedListener(this)
        b.ibPickup.setOnClickListener(this)
    }

    private fun checkStatusPickup() {
        mainViewModel.checkStatusPickup(
            preferenceManager.getUserId(Constants.KEY_USER_ID)!!
        )
        observe()
    }

    private fun observe() {
        mainViewModel.loading.observe(this, { loading ->
            if (loading == false) b.progressbar.visibility = GONE
        })
        mainViewModel.status.observe(this, { status ->
            if (status == true){
                mainViewModel.order.observe(this, { order ->
                    this.order = order
                })
            } else {
                mainViewModel.errorMessage.observe(this, { message ->
                    Log.e("TAG", "Error message: $message")
                })
            }
        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_home -> {
                home()
                return true
            }
            R.id.menu_cart -> {
                cart()
                return true
            }
            R.id.menu_history -> {
                history()
                return true
            }
            R.id.menu_profile -> {
                profile()
                return true
            }
        }
        return false
    }

    private fun home() {
        val fragment: Fragment = HomeFragment(order)
        val manager = supportFragmentManager
        manager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }

    private fun cart() {
        val fragment: Fragment = CartFragment()
        val manager = supportFragmentManager
        manager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }

    private fun history() {
        val fragment: Fragment = HistoryFragment()
        val manager = supportFragmentManager
        manager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }

    private fun profile() {
        val fragment: Fragment = ProfileFragment()
        val manager = supportFragmentManager
        manager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ib_pickup -> pickup()
        }
    }

    private fun pickup() {
        if (order.status == "active"){
            showToast(this, "Penjemputan kamu sudah aktif")
        } else {
            startActivity(Intent(this, PickupCategoryActivity::class.java))
        }
    }
}