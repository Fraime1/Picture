package com.fraime.android.picture.domain.usecase.login

import com.fraime.android.picture.domain.repository.LoginRepository
import com.google.firebase.auth.FirebaseUser

class GetCurrentUserUseCase (private val loginRepository: LoginRepository) {
    fun execute() : FirebaseUser? {
        return loginRepository.getCurrentUser()
    }
}