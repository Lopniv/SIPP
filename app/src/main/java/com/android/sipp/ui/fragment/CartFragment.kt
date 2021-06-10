package com.android.sipp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.android.sipp.R
import com.android.sipp.adapter.ItemRecycleAdapter
import com.android.sipp.databinding.FragmentCartBinding
import com.android.sipp.databinding.FragmentHomeBinding
import com.android.sipp.utils.Dummy.generateItem

class CartFragment : Fragment() {

    private lateinit var itemRecycleAdapter: ItemRecycleAdapter

    private var binding : FragmentCartBinding? = null
    private val b get() = binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initiate()
        setupRecyclerView()
    }

    private fun initiate() {
        itemRecycleAdapter = ItemRecycleAdapter(arrayListOf())
    }

    private fun setupRecyclerView() {
        b.rvItem.apply {
            adapter = itemRecycleAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
        itemRecycleAdapter.updateItem(generateItem())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}