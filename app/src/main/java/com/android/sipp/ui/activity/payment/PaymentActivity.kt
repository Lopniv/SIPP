package com.android.sipp.ui.activity.payment

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.ViewModelProvider
import com.android.sipp.R
import com.android.sipp.databinding.ActivityPaymentBinding
import com.android.sipp.model.ItemPickup
import com.android.sipp.preference.Constants
import com.android.sipp.preference.PreferenceManager
import com.android.sipp.ui.activity.main.MainActivity
import com.android.sipp.utils.Utils.showMessage
import com.android.sipp.utils.Utils.showToast
import com.android.sipp.utils.Utils.start
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class PaymentActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val KEY_PAYMENT = "PAYMENT"
    }

    private val localeID = Locale("in", "ID")
    private val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
    private var price: Int? = null
    private var duration: Int = 0
    private var title: String? = null

    private lateinit var preferenceManager: PreferenceManager
    private lateinit var paymentViewModel: PaymentViewModel
    private lateinit var b: ActivityPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(b.root)
        initiate()
        setListener()
        retrieveData()
    }

    private fun initiate() {
        paymentViewModel = ViewModelProvider(this).get(PaymentViewModel::class.java)
        preferenceManager = PreferenceManager(this)
    }

    private fun setListener() {
        b.etDuration.addTextChangedListener(textWatcher)
        b.btnOrder.setOnClickListener(this)
    }

    private fun retrieveData() {
        val intent = intent.getParcelableExtra<ItemPickup>(KEY_PAYMENT)
        price = intent?.price ?: 0
        title = intent?.title ?: "Title"
        if (intent != null) {
            setupData(intent)
        }
    }

    private fun setupData(item: ItemPickup) {
        if (item.title == "Penjemputan Langsung") {
            b.etDuration.visibility = GONE
            b.tvTotalPrice.text = formatRupiah.format(price)
        }
        b.ivPickupPlan.setImageResource(item.image)
        b.tvPickupPlan.text = item.title
        b.tvPricePlan.text = formatRupiah.format(item.price)
        b.tvDescPlan.text = item.desc
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            duration = if (b.etDuration.text.toString().isEmpty()){
                0
            } else {
                b.etDuration.text.toString().toInt()
            }
            val price = price?.times(duration)
            b.tvTotalPrice.text = formatRupiah.format(price)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun order(){
        b.progressbar.visibility = VISIBLE
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("EEEE, MMM d, yyyy")
        val now = dateFormat.format(calendar.time)
        val amountPickup = duration*12
        val name = "${preferenceManager.getFirstName(Constants.KEY_FIRST_NAME)} ${preferenceManager.getLastName(Constants.KEY_LAST_NAME)}"
        Log.e("TAG", "DATE: $now")
        when(title){
            "Penjemputan Terjadwal" -> {
                paymentViewModel.order(
                    preferenceManager.getUserId(Constants.KEY_EMAIL)!!,
                    name,
                    amountPickup,
                    now,
                    "terjadwal",
                    preferenceManager.getType(Constants.KEY_TYPE)!!,
                    "active",
                    "not started",
                    false
                )
            }
            "Penjemputan Langsung" -> {
                paymentViewModel.order(
                    preferenceManager.getUserId(Constants.KEY_EMAIL)!!,
                    name,
                    1,
                    now,
                    "langsung",
                    preferenceManager.getType(Constants.KEY_TYPE)!!,
                    "active",
                    "not started",
                    false
                )
            }
        }
        observe()
    }

    private fun observe(){
        paymentViewModel.loading.observe(this, { loading ->
            if (loading == false) b.progressbar.visibility = GONE
        })
        paymentViewModel.status.observe(this, { status ->
            if (status == true){
                showToast(this, "Kamu berhasil melakukan permintaan penjemputan")
                start(this, MainActivity::class.java)
            } else {
                paymentViewModel.errorMessage.observe(this, { message ->
                    showMessage(b.root, message)
                })
            }
        })
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_order -> order()
        }
    }
}