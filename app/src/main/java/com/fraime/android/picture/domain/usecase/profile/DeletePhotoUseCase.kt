package com.fraime.android.picture.domain.usecase.profile

import androidx.lifecycle.LiveData
import com.fraime.android.picture.domain.repository.ProfileRepository
import kotlinx.coroutines.Deferred

class DeletePhotoUseCase(private val profileRepository : ProfileRepository) {
    suspend fun execute () : Pair<Boolean, String> {
        return profileRepository.deletePhoto()
    }
}