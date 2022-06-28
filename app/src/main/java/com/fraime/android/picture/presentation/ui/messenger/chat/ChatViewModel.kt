package com.fraime.android.picture.presentation.ui.messenger.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fraime.android.picture.domain.usecase.chat.SendGalleryUseCase
import com.fraime.android.picture.domain.usecase.chat.SendMessageUseCase

class ChatViewModel(
    private val sendMessageUseCase: SendMessageUseCase,
    private val sendGalleryUseCase: SendGalleryUseCase
) : ViewModel() {

    var message = MutableLiveData<String>()



    fun sendMessage() {
        sendMessageUseCase.execute(message = message.value!!)
    }




}