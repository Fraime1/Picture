package com.fraime.android.picture.presentation.ui.signup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fraime.android.picture.R
import com.fraime.android.picture.databinding.FragmentSignUpBinding
import com.fraime.android.picture.presentation.ui.utils.ProgressDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val TAG = "SignUpFragment"

class SignUpFragment : Fragment() {


    private lateinit var binding: FragmentSignUpBinding
    private val viewModel by viewModel<SignUpViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false
            )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.errorInputTextEmail.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.emailSignUp.error = it
            } else {
                binding.emailSignUp.error = null
            }
        }
        viewModel.errorInputTextPassword.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.passwordSignUp.error = it
            } else {
                binding.passwordSignUp.error = null
            }
        }

        viewModel.errorInputTextUsername.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.usernameSignUp.error = it
            } else {
                binding.usernameSignUp.error = null
            }
        }
        binding.signUpButton.setOnClickListener {
            viewModel.hideKeyboard(context, it)
            val dialog = ProgressDialogFragment().apply {
                show(this@SignUpFragment.childFragmentManager, ProgressDialogFragment.PROGRESS_DIALOG)
                isCancelable = false
            }
            viewModel.signUp()?.observe(viewLifecycleOwner) {
                if (it?.first == true) {
                    dialog.dismiss()
                    findNavController().navigate(R.id.action_signUpWithEmailFragment_to_navigation)
                } else {
                    dialog.dismiss()
                    viewModel.failedTextSignUp.value = it?.second
                }
            } ?: dialog.dismiss()
        }
    }
}