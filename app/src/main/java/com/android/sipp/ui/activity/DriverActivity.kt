package com.android.sipp.ui.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.sipp.adapter.DriverPickupIndustryAdapter
import com.android.sipp.adapter.DriverPickupPersonalAdapter
import com.android.sipp.databinding.ActivityDriverBinding
import com.android.sipp.model.Industry
import com.android.sipp.model.Personal
import com.android.sipp.utils.Utils.FirestoreKeys.COLLECTION_INDUSTRY
import com.android.sipp.utils.Utils.FirestoreKeys.COLLECTION_PICKUP
import com.android.sipp.utils.Utils.FirestoreKeys.COLLECTION_USER
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_EMAIL
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_FIRST_NAME
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_ID
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_LAST_NAME
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_PHONE
import com.android.sipp.utils.Utils.FirestoreKeys.FIELD_TYPE
import com.google.firebase.firestore.FirebaseFirestore


class DriverActivity : AppCompatActivity() {

    private var email: ArrayList<String> = arrayListOf()
    private var personalList: ArrayList<Personal> = arrayListOf()
    private var industryList: ArrayList<Industry> = arrayListOf()

    private lateinit var b: ActivityDriverBinding
    private lateinit var personalAdapter: DriverPickupPersonalAdapter
    private lateinit var industryAdapter: DriverPickupIndustryAdapter
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityDriverBinding.inflate(layoutInflater)
        setContentView(b.root)
        initiate()
        initRecyclerView()
        getData()
    }

    private fun initiate() {
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
                    Log.e("TAG","Data: ${document.data[FIELD_EMAIL]}")
                    email.add(document.data[FIELD_EMAIL] as String)
                }
                findUserIdInPersonal()
            }
    }

    private fun findUserIdInPersonal() {
        Log.e("TAG", "EMAIL SIZE : ${email.size}")
        for(item in email){
            Log.e("TAG", "EMAIL : $item")
            firestore.collection(COLLECTION_USER)
                .whereEqualTo(FIELD_EMAIL, item)
                .get()
                .addOnCompleteListener{
                    if (it.isSuccessful){
                        if (it.result?.isEmpty != true){
                            val data = it.result?.documents?.get(0)
                            val personal = Personal(
                                data?.getString(FIELD_ID)!!,
                                data.getString(FIELD_FIRST_NAME)!!,
                                data.getString(FIELD_LAST_NAME)!!,
                                data.getString(FIELD_EMAIL)!!,
                                data.getString(FIELD_PHONE)!!,
                                data.getString(FIELD_TYPE)!!
                            )
                            Log.e("TAG", "EMAIL : $personal")
                            personalList.add(personal)
                        }
                    }
                }
        }
        findUserIdInIndustry()
    }

    private fun findUserIdInIndustry() {
        for(item in email){
            firestore.collection(COLLECTION_INDUSTRY)
                .whereEqualTo(FIELD_EMAIL, item)
                .get()
                .addOnCompleteListener{
                    if (it.isSuccessful){
                        if(it.result?.documents?.size!! > 0){
                            val data = it.result?.documents?.get(0)
                            val industry = Industry(
                                data?.getString(FIELD_ID)!!,
                                data.getString(FIELD_FIRST_NAME)!!,
                                data.getString(FIELD_LAST_NAME)!!,
                                data.getString(FIELD_EMAIL)!!,
                                data.getString(FIELD_PHONE)!!,
                                data.getString(FIELD_TYPE)!!
                            )
                            industryList.add(industry)
                            setupRecyclerView()
                        }
                    }
                }
        }
        Handler(Looper.getMainLooper()).postDelayed({
            setupRecyclerView()
        }, 1000)
    }

    private fun setupRecyclerView() {
        personalAdapter.updatePersonalItem(personalList)
        industryAdapter.updatePersonalItem(industryList)
        b.progressbar.visibility = GONE
    }
}