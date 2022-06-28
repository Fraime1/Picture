package com.fraime.android.picture.domain.usecase.chat

import com.fraime.android.picture.domain.repository.ChatRepository

class SendMessageUseCase(private val chatRepository: ChatRepository) {

    fun execute(message: String) {
        chatRepository.sendMessage(message)
    }
}