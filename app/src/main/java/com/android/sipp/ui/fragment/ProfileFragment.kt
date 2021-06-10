package com.android.sipp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.sipp.R
import com.android.sipp.databinding.FragmentHistoryBinding
import com.android.sipp.databinding.FragmentProfileBinding
import com.android.sipp.preference.Constants.KEY_EMAIL
import com.android.sipp.preference.Constants.KEY_FIRST_NAME
import com.android.sipp.preference.Constants.KEY_LAST_NAME
import com.android.sipp.preference.Constants.KEY_PHONE
import com.android.sipp.preference.PreferenceManager

class ProfileFragment : Fragment(), View.OnClickListener {

    private lateinit var pref: PreferenceManager

    private var binding : FragmentProfileBinding? = null
    private val b get() = binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initiate()
        setData()
        setListener()
    }

    private fun initiate() {
        pref = PreferenceManager(requireContext())
    }

    @SuppressLint("SetTextI18n")
    private fun setData() {
        b.tvName.text = "${pref.getFirstName(KEY_FIRST_NAME)} ${pref.getLastName(KEY_LAST_NAME)}"
        b.tvEmail.text = pref.getEmail(KEY_EMAIL)
        b.tvPhone.text = pref.getPhone(KEY_PHONE)
    }

    private fun setListener() {
        b.btnLogout.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_logout){
            val dialog = LogoutDialogFragment()
            dialog.show(parentFragmentManager, "DIALOG")
        }
    }
}