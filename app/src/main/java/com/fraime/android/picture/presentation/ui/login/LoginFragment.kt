package com.fraime.android.picture.presentation.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fraime.android.picture.R
import com.fraime.android.picture.databinding.FragmentLoginBinding
import com.fraime.android.picture.domain.model.AppState
import com.fraime.android.picture.presentation.ui.utils.ProgressDialogFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val TAG = "LoginFragment"

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var navBot: BottomNavigationView
    private lateinit var toolBar: Toolbar
    private val viewModel by viewModel<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        navBot = requireActivity().findViewById(R.id.bottomNavigationView)
        navBot.visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.errorInputTextEmail.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.signInLogin.error = it
            } else {
                binding.signInLogin.error = null
            }
        }
        viewModel.errorInputTextPassword.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.signInPassword.error = it
            } else {
                binding.signInPassword.error = null
            }
        }

        binding.createAccount.setOnClickListener {
            findNavController().navigate(
                R.id.action_loginFragment_to_signUpWithEmailFragment
            )
        }
        binding.signInWithEmailButton.setOnClickListener {
            viewModel.hideKeyboard(context, it)
            val dialog = ProgressDialogFragment().apply {
                show(this@LoginFragment.childFragmentManager,
                    ProgressDialogFragment.PROGRESS_DIALOG)
                isCancelable = false
            }
            viewModel.signIn()?.observe(viewLifecycleOwner) {
                if (it?.first == true) {
                    dialog.dismiss()
                    CoroutineScope(Dispatchers.Main).launch {
                        AppState.updateState(AppState.ONLINE).await()
                        findNavController().navigate(R.id.action_loginFragment_to_navigation)
                    }
                } else {
                    dialog.dismiss()
                    viewModel.failedSignInText.value = it?.second
                }
            } ?: dialog.dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.checkCurrentUser(findNavController())
    }

    override fun onDetach() {
        super.onDetach()
        navBot.visibility = View.VISIBLE
    }

}