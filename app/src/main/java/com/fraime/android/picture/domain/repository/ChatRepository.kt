package com.fraime.android.picture.domain.repository


interface ChatRepository {
    fun sendMessage(text: String)
}