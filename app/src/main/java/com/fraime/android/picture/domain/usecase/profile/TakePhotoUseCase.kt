package com.fraime.android.picture.domain.usecase.profile

import android.app.Activity
import android.content.Context
import android.net.Uri
import com.fraime.android.picture.domain.repository.ProfileRepository

class TakePhotoUseCase(private val profileRepository: ProfileRepository) {
    suspend fun execute(photo: Uri) : Boolean{
        return profileRepository.takePhoto(photo)
    }
}