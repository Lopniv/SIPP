package com.android.sipp.ui.activity.driver

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.sipp.R
import com.android.sipp.adapter.DriverPickupIndustryAdapter
import com.android.sipp.adapter.DriverPickupPersonalAdapter
import com.android.sipp.databinding.ActivityDriverBinding
import com.android.sipp.model.Order
import com.android.sipp.preference.PreferenceManager
import com.android.sipp.ui.activity.driver.DetailDriverPickupActivity.Companion.ORDER_DATA
import com.android.sipp.ui.activity.intro.IntroActivity
import com.android.sipp.ui.fragment.LogoutDialogFragment
import com.android.sipp.utils.Listener
import com.android.sipp.utils.Utils
import com.android.sipp.utils.Utils.FirestoreKeys.COLLECTION_PICKUP
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_ADDRESS
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_AMOUNT_PICKUP
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_EMAIL
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_NAME
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_PICKUP_TYPE
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_START_DATE
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_STATUS
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_STATUS_PAYMENT
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_STATUS_PICKUP
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_TYPE
import com.google.firebase.firestore.FirebaseFirestore


class DriverActivity : AppCompatActivity(), Listener, View.OnClickListener {

    private var email: ArrayList<String> = arrayListOf()
    private var personalList: ArrayList<Order> = arrayListOf()
    private var industryList: ArrayList<Order> = arrayListOf()
    private var orders: ArrayList<Order> = arrayListOf()

    private lateinit var b: ActivityDriverBinding
    private lateinit var personalAdapter: DriverPickupPersonalAdapter
    private lateinit var industryAdapter: DriverPickupIndustryAdapter
    private lateinit var firestore: FirebaseFirestore
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityDriverBinding.inflate(layoutInflater)
        setContentView(b.root)
        setListener()
        initiate()
        initRecyclerView()
        getData()
    }

    private fun setListener() {
        b.btnLogout.setOnClickListener(this)
    }

    private fun initiate() {
        preferenceManager = PreferenceManager(this)
        personalAdapter = DriverPickupPersonalAdapter(arrayListOf(), this)
        industryAdapter = DriverPickupIndustryAdapter(arrayListOf(), this)
    }

    private fun initRecyclerView() {
        b.rvPersonal.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = personalAdapter
        }
        b.rvIndustry.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = industryAdapter
        }
    }

    private fun getData() {
        b.progressbar.visibility = VISIBLE
        firestore = FirebaseFirestore.getInstance()
        firestore.collection(COLLECTION_PICKUP)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.e("TAG", "Data: ${document.data[FIELD_EMAIL]}")
                    val amountPickup = document.data[FIELD_AMOUNT_PICKUP] as Long
                    val order = Order(
                        document.data[FIELD_EMAIL] as String,
                        document.data[FIELD_NAME] as String,
                        amountPickup.toInt(),
                        document.data[FIELD_START_DATE] as String,
                        document.data[FIELD_PICKUP_TYPE] as String,
                        document.data[FIELD_TYPE] as String,
                        document.data[FIELD_STATUS] as String,
                        document.data[FIELD_STATUS_PICKUP] as String,
                        document.data[FIELD_STATUS_PAYMENT] as Boolean,
                        document.data[FIELD_ADDRESS] as String
                    )
                    orders.add(order)
                }
                Handler(Looper.getMainLooper()).postDelayed({
                    checkTypePickup()
                }, 2000)
            }
    }

    private fun checkTypePickup() {
        for (item in orders) {
            if (item.type == "personal") {
                personalList.add(item)
            } else if (item.type == "industry") {
                industryList.add(item)
            }
        }
        setupRecyclerView()
    }

//    private fun findUserIdInPersonal() {
//        Log.e("TAG", "EMAIL SIZE : ${email.size}")
//        for(item in email){
//            Log.e("TAG", "EMAIL : $item")
//            firestore.collection(COLLECTION_USER)
//                .whereEqualTo(FIELD_EMAIL, item)
//                .get()
//                .addOnCompleteListener{
//                    if (it.isSuccessful){
//                        if (it.result?.isEmpty != true){
//                            val data = it.result?.documents?.get(0)
//                            val personal = Personal(
//                                data?.getString(FIELD_ID)!!,
//                                data.getString(FIELD_FIRST_NAME)!!,
//                                data.getString(FIELD_LAST_NAME)!!,
//                                data.getString(FIELD_EMAIL)!!,
//                                data.getString(FIELD_PHONE)!!,
//                                data.getString(FIELD_TYPE)!!
//                            )
//                            Log.e("TAG", "EMAIL : $personal")
//                            personalList.add(personal)
//                        }
//                    }
//                }
//        }
//        findUserIdInIndustry()
//    }
//
//    private fun findUserIdInIndustry() {
//        for(item in email){
//            firestore.collection(COLLECTION_INDUSTRY)
//                .whereEqualTo(FIELD_EMAIL, item)
//                .get()
//                .addOnCompleteListener{
//                    if (it.isSuccessful){
//                        if(it.result?.documents?.size!! > 0){
//                            val data = it.result?.documents?.get(0)
//                            val industry = Industry(
//                                data?.getString(FIELD_ID)!!,
//                                data.getString(FIELD_FIRST_NAME)!!,
//                                data.getString(FIELD_LAST_NAME)!!,
//                                data.getString(FIELD_EMAIL)!!,
//                                data.getString(FIELD_PHONE)!!,
//                                data.getString(FIELD_TYPE)!!
//                            )
//                            industryList.add(industry)
//                            setupRecyclerView()
//                        }
//                    }
//                }
//        }
//        Handler(Looper.getMainLooper()).postDelayed({
//            setupRecyclerView()
//        }, 1000)
//    }

    private fun setupRecyclerView() {
        personalAdapter.updatePersonalItem(personalList)
        industryAdapter.updateIndustryItem(industryList)
        personalAdapter.listener = this
        industryAdapter.listener = this
        b.progressbar.visibility = GONE
    }

    override fun onItemClick(order: Order) {
        val detail = Intent(this, DetailDriverPickupActivity::class.java)
        detail.putExtra(ORDER_DATA, order)
        startActivity(detail)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_logout){
            val dialog = LogoutDialogFragment()
            dialog.show(supportFragmentManager, "DIALOG")
        }
    }
}