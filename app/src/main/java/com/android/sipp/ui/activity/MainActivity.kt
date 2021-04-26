package com.android.sipp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.sipp.R
import com.android.sipp.databinding.ActivityMainBinding
import com.android.sipp.databinding.FragmentCartBinding
import com.android.sipp.ui.fragment.CartFragment
import com.android.sipp.ui.fragment.HistoryFragment
import com.android.sipp.ui.fragment.HomeFragment
import com.android.sipp.ui.fragment.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener,
    View.OnClickListener {

    private lateinit var b: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)
        setListener()
        home()
    }

    private fun setListener() {
        b.bnv.setOnNavigationItemSelectedListener(this)
        b.ibPickup.setOnClickListener(this)
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
        val fragment: Fragment = HomeFragment()
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
        startActivity(Intent(this, PickupCategoryActivity::class.java))
    }
}