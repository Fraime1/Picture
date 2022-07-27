package com.fraime.android.picture.presentation.ui.profile.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.fraime.android.picture.R
import com.fraime.android.picture.databinding.FragmentFriendsBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class FirendsFragment : Fragment() {

    private lateinit var binding : FragmentFriendsBinding
    private lateinit var navBot: BottomNavigationView
    private val viewModel by viewModel<FriendsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_friends, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        initialPager()
        navBot = requireActivity().findViewById(R.id.bottomNavigationView)
        navBot.visibility = View.GONE
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        navBot.visibility = View.VISIBLE
    }

    // All work with ViewPager2 and TabLayout
    // .attach() - starts TabLayoutMediator()
    private fun initialPager() {
        binding.viewPager.adapter = PagerAdapter(requireActivity())
//        binding.tabLayout.tabIconTint = null
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = requireActivity().getString(R.string.firends_tab)
                else -> tab.text = requireActivity().getString(R.string.firends_requests_tab)
            }
        }.attach()
    }
}