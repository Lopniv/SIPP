package com.android.sipp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.sipp.R
import com.android.sipp.adapter.ItemRecycleAdapter.*
import com.android.sipp.databinding.ItemRecycleBinding
import com.android.sipp.model.ItemRecycle
import com.bumptech.glide.Glide

class ItemRecycleAdapter(var items: ArrayList<ItemRecycle>): RecyclerView.Adapter<ViewHolder>() {

    fun updateItem(items: ArrayList<ItemRecycle>){
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_recycle, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val b = ItemRecycleBinding.bind(itemView)
        fun bind(itemRecycle: ItemRecycle) {
            b.tvNameItem.text = itemRecycle.name
            b.tvPriceItem.text = itemRecycle.price
            Glide.with(itemView.context)
                .load(itemRecycle.image)
                .placeholder(R.drawable.ic_refresh)
                .into(b.ivItem)
        }
    }
}