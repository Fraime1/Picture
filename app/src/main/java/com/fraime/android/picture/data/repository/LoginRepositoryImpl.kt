package com.fraime.android.picture.data.repository

import androidx.lifecycle.LiveData
import com.fraime.android.picture.data.firebase.FirebaseAuthPicture
import com.fraime.android.picture.domain.repository.LoginRepository
import com.google.firebase.auth.FirebaseUser

class LoginRepositoryImpl(private val firebaseAuthPicture: FirebaseAuthPicture) : LoginRepository{
    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuthPicture.getCurrentUser()
    }

    override fun signInWithEmail(email: String, password: String) : LiveData<Pair<Boolean, String>> {
        return firebaseAuthPicture.signInWithEmailAndPassword(email, password)
    }
}