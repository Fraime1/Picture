package com.fraime.android.picture.domain.usecase.profile

import com.fraime.android.picture.domain.repository.ProfileRepository

class ReauthenticateUseCase (private val profileRepository: ProfileRepository) {
    suspend fun execute(email: String, password: String) : Boolean {
        return profileRepository.reauthenticate(email, password)
    }
}