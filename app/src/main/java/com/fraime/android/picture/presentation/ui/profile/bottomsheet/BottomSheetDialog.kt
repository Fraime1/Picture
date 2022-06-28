package com.fraime.android.picture.presentation.ui.profile.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResult
import com.fraime.android.picture.R
import com.fraime.android.picture.databinding.FragmentBottomSheetDialogBinding
import com.fraime.android.picture.presentation.ui.profile.emailchange.EmailChangeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class BottomSheetDialog : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomSheetDialogBinding
    private val viewModel by viewModel<BottomSheetDialogViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_sheet_dialog, container, false)
        binding.viewMidel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.errorTextInputEmail.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.emailBottomSheet.error = it
            } else {
                binding.emailBottomSheet.error = null
            }
        }

        viewModel.errorTextInputPassword.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.passwordBottomSheet.error = it
            } else {
                binding.passwordBottomSheet.error = null
            }
        }

        binding.button3.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                if (viewModel.reauthenticate()) {
                    setFragmentResult(REQUEST_CODE, bundleOf(RESULT_CODE to true))
                } else {
                    setFragmentResult(REQUEST_CODE, bundleOf(RESULT_CODE to false))
                }
            }
        }
    }

    companion object {
        const val REQUEST_CODE = "BottomSheetRequestCode"
        const val BOTTOM_SHEET_DIALOG = "BottomSheetDialog"
        const val RESULT_CODE = "BottomSheetDialogResultCode"
    }
}