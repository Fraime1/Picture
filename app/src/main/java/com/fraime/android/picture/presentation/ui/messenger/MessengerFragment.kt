package com.fraime.android.picture.presentation.ui.messenger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.fraime.android.picture.R
import com.fraime.android.picture.databinding.FragmentMessengerBinding


class MessengerFragment : Fragment() {

    private lateinit var binding: FragmentMessengerBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_messenger, container, false)
        binding.button.setOnClickListener{
            findNavController().navigate(R.id.action_messengerFragment_to_chatFragment2)
        }
        return binding.root
    }
}