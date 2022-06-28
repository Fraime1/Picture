package com.fraime.android.picture.domain.usecase.profile

import com.fraime.android.picture.domain.repository.ProfileRepository

class ChangePasswordUseCase (private val profileRepository: ProfileRepository) {

    suspend fun execute(password: String) : Boolean {
       return profileRepository.changePassword(password)
    }
 }