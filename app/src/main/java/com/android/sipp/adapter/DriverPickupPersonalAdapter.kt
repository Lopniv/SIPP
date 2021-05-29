package com.android.sipp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.sipp.R
import com.android.sipp.databinding.ItemDriverPickupBinding
import com.android.sipp.model.Personal
import java.util.*

class DriverPickupPersonalAdapter (var personal: ArrayList<Personal>, var context: Context) :
    RecyclerView.Adapter<DriverPickupPersonalAdapter.DriverPickupViewHolder>() {

    fun updatePersonalItem(personal: ArrayList<Personal>) {
        this.personal.clear()
        this.personal.addAll(personal)
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
        holder.bind(personal[position])
    }

    override fun getItemCount() = personal.size

    class DriverPickupViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val b = ItemDriverPickupBinding.bind(view)
        private val customerName = b.tvName
        private val customerEmail = b.tvEmail
        @SuppressLint("SetTextI18n")
        fun bind(personal: Personal){
            customerName.text = "${personal.firstName} ${personal.lastName}"
            customerEmail.text = personal.email
        }
    }
}