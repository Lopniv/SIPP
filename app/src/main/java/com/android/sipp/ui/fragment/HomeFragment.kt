package com.android.sipp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.sipp.adapter.NewsAdapter
import com.android.sipp.databinding.FragmentHomeBinding
import com.android.sipp.model.Order
import com.android.sipp.utils.Dummy.generateNews

class HomeFragment(val order: Order) : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var newsAdapter: NewsAdapter

    private var binding : FragmentHomeBinding? = null
    private val b get() = binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b.refresh.setOnRefreshListener(this)
        initiate()
        setupData()
        setupRecyclerView()
    }

    private fun initiate() {
        newsAdapter = NewsAdapter(arrayListOf())
    }

    @SuppressLint("SetTextI18n")
    private fun setupData() {
        if (order.status == "active"){
            if (!order.statusPayment){
                b.tvPayment.text = "Belum dibayar"
            } else {
                b.tvPayment.text = "Sudah dibayar"
            }
            b.tvAmountPickup.text = order.amountPickup.toString()
            b.tvStartDate.text = order.startDate
            b.tvStatus.text = order.status
            b.placeholderAlreadyPickup.visibility = VISIBLE
            b.placeholderNoPickup.visibility = GONE
        } else {
            b.placeholderAlreadyPickup.visibility = GONE
            b.placeholderNoPickup.visibility = VISIBLE
        }
    }

    private fun setupRecyclerView() {
        b.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        newsAdapter.updateNews(generateNews())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onRefresh() {
        setupData()
        b.refresh.isRefreshing = false
    }
}