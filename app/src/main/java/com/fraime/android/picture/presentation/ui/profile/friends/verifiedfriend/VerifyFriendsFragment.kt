package com.fraime.android.picture.presentation.ui.profile.friends.verifiedfriend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.fraime.android.picture.R
import com.fraime.android.picture.databinding.FragmentVerifyFriendsBinding


class VerifyFriendsFragment : Fragment() {

    private lateinit var binding: FragmentVerifyFriendsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_verify_friends, container, false)
        return binding.root
    }
}