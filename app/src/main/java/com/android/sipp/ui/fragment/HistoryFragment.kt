package com.android.sipp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.sipp.R
import com.android.sipp.adapter.HistoryAdapter
import com.android.sipp.databinding.FragmentCartBinding
import com.android.sipp.databinding.FragmentHistoryBinding
import com.android.sipp.utils.Dummy.generateHistories

class HistoryFragment : Fragment() {

    private lateinit var historyAdapter: HistoryAdapter

    private var binding : FragmentHistoryBinding? = null
    private val b get() = binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initiate()
        setupRecyclerView()
    }

    private fun initiate() {
        historyAdapter = HistoryAdapter(arrayListOf())
    }

    private fun setupRecyclerView() {
        b.rvHistories.apply {
            adapter = historyAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        historyAdapter.updateHistories(generateHistories())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}