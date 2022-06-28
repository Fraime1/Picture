package com.fraime.android.picture.domain.model

import com.fraime.android.picture.data.firebase.FirebaseAuthPicture
import com.fraime.android.picture.data.firebase.FirebaseRDPicture
import kotlinx.coroutines.*

enum class AppState (val state: String) {
    ONLINE("Online"),
    OFFLINE("last seen recently"),
    TYPING("typing...");

    companion object {
        suspend fun updateState(appState: AppState)  = withContext(Dispatchers.IO){
            async {
                FirebaseRDPicture().updateState(appState = appState, user = FirebaseAuthPicture().getCurrentUser())
            }
        }
    }
}