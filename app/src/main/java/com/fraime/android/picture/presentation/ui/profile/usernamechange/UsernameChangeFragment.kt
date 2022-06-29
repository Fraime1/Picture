package com.fraime.android.picture.presentation.ui.profile.usernamechange

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fraime.android.picture.R
import com.fraime.android.picture.databinding.FragmentUsernameChangeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val TAG = "UsernameChangeFragment"

class UsernameChangeFragment : Fragment() {

    private lateinit var navBot : BottomNavigationView
    private lateinit var binding: FragmentUsernameChangeBinding
    private val viewModel by viewModel<UsernameChangeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_username_change, container, false)
        navBot = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.username.value = arguments?.getString(USERNAME)
        viewModel.oldUsername.value = arguments?.getString(USERNAME)
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
        inflater.inflate(R.menu.fragment_username_change, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.saveUsername -> {
                viewModel.saveUsername(findNavController())
                Log.d(TAG, "Enter")
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
        const val USERNAME = "UsernameChangeFragmentUsername"
    }

}