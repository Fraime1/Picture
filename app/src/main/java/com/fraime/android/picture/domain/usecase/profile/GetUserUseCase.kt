package com.fraime.android.picture.domain.usecase.profile

import com.fraime.android.picture.domain.model.User
import com.fraime.android.picture.domain.repository.ProfileRepository

class GetUserUseCase (private val profileRepository: ProfileRepository) {
    suspend fun execute () : User {
        return profileRepository.getUser()
    }
}