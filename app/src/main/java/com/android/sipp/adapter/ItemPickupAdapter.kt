package com.android.sipp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.viewpager.widget.PagerAdapter
import com.android.sipp.R
import com.android.sipp.model.ItemPickup
import com.android.sipp.ui.activity.PaymentActivity
import com.android.sipp.ui.activity.PaymentActivity.Companion.KEY_PAYMENT
import com.google.android.material.button.MaterialButton
import java.text.NumberFormat
import java.util.*

class ItemPickupAdapter(var models: ArrayList<ItemPickup>, var context: Context) : PagerAdapter() {

    fun updateItem(model: ArrayList<ItemPickup>) {
        models.clear()
        models.addAll(model)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return models.size
    }

    override fun isViewFromObject(@NonNull view: View, @NonNull `object`: Any): Boolean {
        return view == `object`
    }

    @NonNull
    override fun instantiateItem(@NonNull container: ViewGroup, position: Int): Any {
        val view: View = LayoutInflater.from(container.context).inflate(
            R.layout.item_pickup,
            container,
            false
        )
        val imageView : ImageView = view.findViewById(R.id.iv_pickup_plan)
        val title : TextView = view.findViewById(R.id.tv_pickup_plan)
        val price : TextView = view.findViewById(R.id.tv_price_plan)
        val desc : TextView = view.findViewById(R.id.tv_desc_plan)
        val btnChoose: MaterialButton = view.findViewById(R.id.btn_choose_plan)
        val localeID = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
        imageView.setImageResource(models[position].image)
        title.text = models[position].title
        price.text = formatRupiah.format(models[position].price)
        desc.text = models[position].desc
        btnChoose.setOnClickListener{
            val intent = Intent(context, PaymentActivity::class.java)
            intent.putExtra(KEY_PAYMENT, models[position])
            context.startActivity(intent)
        }
        container.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}