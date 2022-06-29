package com.fraime.android.picture.presentation.ui.profile.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.fraime.android.picture.R
import com.fraime.android.picture.databinding.FragmentFriendsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FirendsFragment : Fragment() {

    private lateinit var binding : FragmentFriendsBinding
    private val viewModel by viewModel<FriendsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_friends, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}