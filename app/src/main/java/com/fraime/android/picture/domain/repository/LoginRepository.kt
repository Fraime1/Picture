package com.fraime.android.picture.domain.repository

import android.text.BoringLayout
import androidx.lifecycle.LiveData
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseUser

interface LoginRepository {

    fun getCurrentUser() : FirebaseUser?

    fun signInWithEmail(email: String, password: String) : LiveData<Pair<Boolean, String>>
}