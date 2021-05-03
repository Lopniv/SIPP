package com.android.sipp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.android.sipp.R
import com.android.sipp.databinding.ActivityPaymentBinding
import com.android.sipp.model.ItemPickup
import java.text.NumberFormat
import java.util.*

class PaymentActivity : AppCompatActivity() {

    companion object {
        const val KEY_PAYMENT = "PAYMENT"
    }
    private val localeID = Locale("in", "ID")
    private val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
    private var price: Int? = null
    private var duration: Int = 0

    private lateinit var b: ActivityPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(b.root)
        setObject()
        retreiveData()
    }

    private fun setObject() {
        b.etDuration.addTextChangedListener(textWatcher)
    }

    private fun retreiveData() {
        val intent = intent.getParcelableExtra<ItemPickup>(KEY_PAYMENT)
        price = intent?.price ?:0
        if (intent != null) {
            setupData(intent)
        }
    }

    private fun setupData(item: ItemPickup) {
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
}