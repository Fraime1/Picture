package com.fraime.android.picture.presentation.ui.profile.emailchange

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fraime.android.picture.R
import com.fraime.android.picture.databinding.FragmentEmailChangeBinding
import com.fraime.android.picture.presentation.ui.profile.usernamechange.UsernameChangeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val TAG = "EmailChangeFragmentTAG"

class EmailChangeFragment : Fragment() {
    private lateinit var navBot : BottomNavigationView
    private lateinit var binding : FragmentEmailChangeBinding
    private val viewModel by viewModel<EmailChangeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_email_change, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        navBot = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        viewModel.email.value = arguments?.getString(EMAIL)
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
                viewModel.saveEmail(findNavController(), this@EmailChangeFragment.childFragmentManager)
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

    companion object {
        const val EMAIL = "EmailChangeFragment"
    }
}