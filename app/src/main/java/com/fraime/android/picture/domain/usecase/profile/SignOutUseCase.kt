package com.fraime.android.picture.domain.usecase.profile

import com.fraime.android.picture.domain.repository.ProfileRepository

class SignOutUseCase(private val profileRepository: ProfileRepository) {

    fun execute(){
        profileRepository.signOut()
    }
}