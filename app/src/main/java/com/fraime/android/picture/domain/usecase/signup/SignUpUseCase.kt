package com.fraime.android.picture.domain.usecase.signup

import androidx.lifecycle.LiveData
import com.fraime.android.picture.domain.repository.SignUpRepository

class SignUpUseCase(private val signUpRepository: SignUpRepository) {
    fun execute (email: String, password: String, username: String) : LiveData<Pair<Boolean, String>>{
        return signUpRepository.signUp(email, password, username)
    }
}