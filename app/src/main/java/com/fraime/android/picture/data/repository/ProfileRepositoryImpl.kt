package com.fraime.android.picture.data.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import com.fraime.android.picture.data.firebase.FirebaseAuthPicture
import com.fraime.android.picture.data.firebase.FirebaseRDPicture
import com.fraime.android.picture.data.firebase.FirebaseStoragePicture
import com.fraime.android.picture.domain.model.User
import com.fraime.android.picture.domain.repository.ProfileRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.tasks.await
import java.util.function.BiPredicate

class ProfileRepositoryImpl(
    private val firebaseAuthPicture: FirebaseAuthPicture,
    private val firebaseStoragePicture: FirebaseStoragePicture,
    private val firebaseRDPicture: FirebaseRDPicture
) : ProfileRepository {
    override fun signOut() {
        firebaseAuthPicture.signOut()
    }

    override suspend fun getUser(): User {
        val firebaseUser = firebaseAuthPicture.getCurrentUser()
        val rduser = firebaseRDPicture.getReference().child(FirebaseRDPicture.NODE_USERS).child(firebaseUser?.uid ?: "")
        return User(
            name = rduser.child(FirebaseRDPicture.CHILD_USERNAME).get().await().value as String,
            email = rduser.child(FirebaseRDPicture.CHILD_EMAIL).get().await().value as String,
            photo = rduser.child(FirebaseRDPicture.CHILD_PHOTO_URL).get().await().value as String,
            state = rduser.child(FirebaseRDPicture.CHILD_STATE).get().await().value as String)
    }

    override suspend fun takePhoto(photo: Uri): Boolean {
        return firebaseStoragePicture.takeImage(photo = photo,
            user = firebaseAuthPicture.getCurrentUser())
    }

    override suspend fun deletePhoto(): Pair<Boolean, String> {
        return firebaseStoragePicture.deleteImageProfile(user = firebaseAuthPicture.getCurrentUser())
    }

    override suspend fun changeUsername(text: String): Boolean {
        return firebaseAuthPicture.changeUsername(text)
    }

    override suspend fun changeEmail(email: String): Boolean {
        return firebaseAuthPicture.changeEmail(email)
    }

    override suspend fun reauthenticate(email: String, password: String): Boolean {
        return firebaseAuthPicture.reauthenticate(email, password)
    }

    override suspend fun changePassword(password: String): Boolean {
        return firebaseAuthPicture.changePassword(password)
    }

}