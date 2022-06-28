package com.fraime.android.picture.presentation.ui.profile.passwordchange

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fraime.android.picture.R
import com.fraime.android.picture.databinding.FragmentPasswordChangeBinding
import com.fraime.android.picture.presentation.ui.profile.emailchange.EmailChangeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel

const val TAG = "PasswordChangeFragment"

class PasswordChangeFragment : Fragment() {
    private lateinit var navBot : BottomNavigationView
    private lateinit var binding : FragmentPasswordChangeBinding
    private val viewModel by viewModel<PasswordChangeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_password_change, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        navBot = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navBot.visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.errorInputText.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.textInputLayout.error = it
            } else {
                binding.textInputLayout.error = null
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_email_change, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.saveEmail -> {
                Log.d(TAG, "Enter")
                viewModel.savePassword(findNavController(), this@PasswordChangeFragment.childFragmentManager)
                true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        navBot.visibility = View.VISIBLE
    }
}