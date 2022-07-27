package com.fraime.android.picture.presentation.ui.profile.friends.requestfirend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.fraime.android.picture.R
import com.fraime.android.picture.databinding.FragmentRequestFriendsBinding
import com.fraime.android.picture.databinding.FragmentVerifyFriendsBinding


class RequestFriendsFragment : Fragment() {

    private lateinit var binding: FragmentRequestFriendsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_request_friends, container, false)
        return binding.root
    }
}