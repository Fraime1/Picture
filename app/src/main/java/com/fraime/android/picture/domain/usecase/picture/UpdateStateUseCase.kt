package com.fraime.android.picture.domain.usecase.picture

import com.fraime.android.picture.domain.model.AppState
import com.fraime.android.picture.domain.repository.PictureRepository

class UpdateStateUseCase(private val pictureRepository: PictureRepository) {
    suspend fun execute(appState: AppState) {
        pictureRepository.updateState(appState= appState)
    }
}