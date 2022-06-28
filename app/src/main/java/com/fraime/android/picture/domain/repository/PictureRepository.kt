package com.fraime.android.picture.domain.repository

import com.fraime.android.picture.domain.model.AppState
import com.google.firebase.auth.FirebaseUser

interface PictureRepository {

    suspend fun updateState(appState: AppState)
}