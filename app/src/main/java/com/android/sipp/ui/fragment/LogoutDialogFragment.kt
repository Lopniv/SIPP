package com.android.sipp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.android.sipp.databinding.LayoutLogoutBinding
import com.android.sipp.preference.PreferenceManager
import com.android.sipp.ui.activity.intro.IntroActivity
import com.android.sipp.utils.Utils.start

class LogoutDialogFragment : DialogFragment() {

    private var binding: LayoutLogoutBinding? = null
    private val b get() = binding!!

    private lateinit var preferenceManager: PreferenceManager

    companion object {

        const val TAG = "SimpleDialog"

        private const val KEY_TITLE = "KEY_TITLE"
        private const val KEY_SUBTITLE = "KEY_SUBTITLE"

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutLogoutBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferenceManager = PreferenceManager(requireContext())
        setupClickListeners()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupClickListeners() {
        b.btnNegative.setOnClickListener {
            // TODO: Do some task here
            dismiss()
        }
        b.btnPositive.setOnClickListener {
            preferenceManager.clearUserCredentials()
            start(requireContext(), IntroActivity::class.java)
        }
    }

}