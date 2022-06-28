package com.fraime.android.picture.domain.usecase.profile

import com.fraime.android.picture.domain.repository.ProfileRepository

class ChangeUsernameUseCase (private val profileRepository: ProfileRepository) {
    suspend fun execute(text: String) : Boolean {
        return profileRepository.changeUsername(text)
    }
}