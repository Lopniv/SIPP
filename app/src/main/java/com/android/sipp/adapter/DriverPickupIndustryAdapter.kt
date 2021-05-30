package com.android.sipp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.sipp.R
import com.android.sipp.databinding.ItemDriverPickupBinding
import com.android.sipp.model.Order
import com.android.sipp.utils.Listener
import java.util.ArrayList

class DriverPickupIndustryAdapter (var order: ArrayList<Order>, var context: Context) :
    RecyclerView.Adapter<DriverPickupIndustryAdapter.DriverPickupViewHolder>() {

    var listener: Listener? = null

    fun updateIndustryItem(order: ArrayList<Order>) {
        this.order.clear()
        this.order.addAll(order)
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
        holder.bind(order[position])
        holder.itemView.setOnClickListener{
            listener?.onItemClick(order[position])
        }
    }

    override fun getItemCount() = order.size

    class DriverPickupViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val b = ItemDriverPickupBinding.bind(view)
        private val customerName = b.tvName
        private val customerEmail = b.tvEmail
        private val amountPickup = b.tvAmountPickup
        @SuppressLint("SetTextI18n")
        fun bind(order: Order){
            customerName.text = order.name
            customerEmail.text = order.email
            amountPickup.text = order.amountPickup.toString()
        }
    }
}