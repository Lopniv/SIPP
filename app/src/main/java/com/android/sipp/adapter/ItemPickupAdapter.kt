package com.android.sipp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.viewpager.widget.PagerAdapter
import com.android.sipp.R
import com.android.sipp.model.ItemPickup

class ItemPickupAdapter(var models: ArrayList<ItemPickup>) : PagerAdapter() {

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
        val view: View = LayoutInflater.from(container.context).inflate(R.layout.item_pickup, container, false)
        val imageView : ImageView = view.findViewById(R.id.iv_pickup_plan)
        val title : TextView = view.findViewById(R.id.tv_pickup_plan)
        val desc : TextView = view.findViewById(R.id.tv_desc_plan)
        imageView.setImageResource(models[position].image)
        title.text = models[position].title
        desc.text = models[position].desc
//        view.setOnClickListener {
//            val intent = Intent(context, DetailActivity::class.java)
//            intent.putExtra("param", models!![position].getTitle())
//            context!!.startActivity(intent)
//            // finish();
//        }
        container.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}