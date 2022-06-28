package com.fraime.android.picture.presentation.ui.messenger.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.fraime.android.picture.R
import com.fraime.android.picture.data.repository.ChatRepositoryImpl
import com.fraime.android.picture.databinding.FragmentChatBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding
    private lateinit var navBot: BottomNavigationView
    private val chatViewModel by viewModel<ChatViewModel>()
    private val chatRepository = ChatRepositoryImpl()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)
        binding.lifecycleOwner = this@ChatFragment
        binding.viewModel = chatViewModel
        chatRepository.displayChangedText(binding.recyclerView)
        navBot = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navBot.visibility = View.GONE
        val view = binding.root
        return view
    }

    override fun onDetach() {
        super.onDetach()
        navBot.visibility = View.VISIBLE
    }
}