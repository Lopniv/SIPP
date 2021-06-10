package com.android.sipp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.sipp.R
import com.android.sipp.databinding.ItemNewsBinding
import com.android.sipp.model.News

class NewsAdapter(var news: ArrayList<News>)
    : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(news[position])
    }

    override fun getItemCount(): Int = news.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val b = ItemNewsBinding.bind(itemView)
        fun bind(news: News) {
            b.tvSource.text = news.source
            b.tvTime.text = news.time
            b.tvTitle.text = news.title
            b.tvContent.text = news.content
            b.ivNews.setImageResource(news.image)
        }
    }
}