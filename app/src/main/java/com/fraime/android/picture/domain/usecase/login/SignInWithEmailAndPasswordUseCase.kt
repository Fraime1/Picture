package com.fraime.android.picture.domain.usecase.login

import androidx.lifecycle.LiveData
import com.fraime.android.picture.domain.repository.LoginRepository

class SignInWithEmailAndPasswordUseCase(private val loginRepository: LoginRepository) {

    fun execute(email: String, password: String): LiveData<Pair<Boolean, String>> {
        return loginRepository.signInWithEmail(email, password)
    }
}