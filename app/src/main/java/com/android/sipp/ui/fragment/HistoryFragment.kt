package com.android.sipp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.sipp.R
import com.android.sipp.databinding.FragmentCartBinding
import com.android.sipp.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private var binding : FragmentHistoryBinding? = null
    private val b get() = binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}