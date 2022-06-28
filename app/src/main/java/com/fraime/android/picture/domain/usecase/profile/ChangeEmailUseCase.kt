package com.fraime.android.picture.domain.usecase.profile

import com.fraime.android.picture.domain.repository.ProfileRepository

class ChangeEmailUseCase(private val profileRepository: ProfileRepository) {
    suspend fun execute(email: String) : Boolean {
        return profileRepository.changeEmail(email)
    }
}