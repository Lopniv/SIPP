package com.android.sipp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.sipp.R
import com.android.sipp.adapter.HistoryAdapter.ViewHolder
import com.android.sipp.databinding.ItemHistoryBinding
import com.android.sipp.model.History

class HistoryAdapter(var histories: ArrayList<History>):
    RecyclerView.Adapter<ViewHolder>() {

    fun updateHistories(histories: ArrayList<History>){
        this.histories = histories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(histories[position])
    }

    override fun getItemCount(): Int = histories.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val b = ItemHistoryBinding.bind(itemView)
        fun bind(history: History) {
            b.tvTitle.text = history.title
            b.tvDate.text = history.date
        }
    }
}