package com.android.sipp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.sipp.R
import com.android.sipp.databinding.ItemDriverPickupBinding
import com.android.sipp.model.Industry
import java.util.ArrayList

class DriverPickupIndustryAdapter (var industry: ArrayList<Industry>, var context: Context) :
    RecyclerView.Adapter<DriverPickupIndustryAdapter.DriverPickupViewHolder>() {

    fun updatePersonalItem(industry: ArrayList<Industry>) {
        this.industry.clear()
        this.industry.addAll(industry)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = DriverPickupViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_driver_pickup, parent, false)
    )

    override fun onBindViewHolder(
        holder: DriverPickupViewHolder,
        position: Int
    ) {
        holder.bind(industry[position])
    }

    override fun getItemCount() = industry.size

    class DriverPickupViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val b = ItemDriverPickupBinding.bind(view)
        private val customerName = b.tvName
        private val customerEmail = b.tvEmail
        @SuppressLint("SetTextI18n")
        fun bind(industry: Industry){
            customerName.text = "${industry.firstName} ${industry.lastName}"
            customerEmail.text = industry.email
        }
    }
}