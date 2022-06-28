package com.fraime.android.picture.data.repository

import com.fraime.android.picture.data.firebase.FirebaseAuthPicture
import com.fraime.android.picture.data.firebase.FirebaseRDPicture
import com.fraime.android.picture.domain.model.AppState
import com.fraime.android.picture.domain.repository.PictureRepository

class PictureRepositoryImpl(
    private val firebaseRDPicture: FirebaseRDPicture,
    private val firebaseAuthPicture: FirebaseAuthPicture,
) : PictureRepository {
    override suspend fun updateState(appState: AppState) {
        firebaseRDPicture.updateState(appState = appState, user = firebaseAuthPicture.getCurrentUser())
    }
}