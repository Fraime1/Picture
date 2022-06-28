package com.fraime.android.picture.data.repository

import androidx.lifecycle.LiveData
import com.fraime.android.picture.data.firebase.FirebaseAuthPicture
import com.fraime.android.picture.domain.repository.SignUpRepository

class SignUpRepositoryImpl (private val firebaseAuthPicture: FirebaseAuthPicture) : SignUpRepository{
    override fun signUp(email: String, password: String, username: String): LiveData<Pair<Boolean, String>> {
        return firebaseAuthPicture.signUpWithEmailAndPassword(email, password, username)
    }

}