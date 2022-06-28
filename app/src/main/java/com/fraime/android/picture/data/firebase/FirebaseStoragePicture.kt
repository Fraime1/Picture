package com.fraime.android.picture.data.firebase

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fraime.android.picture.presentation.ui.utils.ProgressDialogFragment
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await


private const val TAG = "FirebaseStoragePicture"

class FirebaseStoragePicture {
    private val reference = FirebaseStorage.getInstance().reference


    fun downloadDefaultImage(user: FirebaseUser?) {
        val path = reference.child("/profile_placeholder.jpg")
        Log.d(TAG, "Enter downloadDefaultImage")
        path.downloadUrl.addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(TAG, "Default download is success!")
                val photoUrl = it.result.toString()
                val profileUpdates = userProfileChangeRequest {
                    photoUri = Uri.parse(photoUrl)
                }
                user?.updateProfile(profileUpdates)
            } else {
                Log.d(TAG, it.exception.toString())
            }
        }
    }

    suspend fun getDefaultImage() : String {
        val path = reference.child("/profile_placeholder.jpg")
        Log.d(TAG, "getDefaultImage(): Enter")
        return path.downloadUrl.await().toString()
    }

    suspend fun takeImage(user: FirebaseUser?, photo: Uri) : Boolean {
        var result = false
        Log.d(TAG, "enter to takeImageFirebase")
        val path = reference.child(user?.uid.toString()).child("profileImage.jpg")
        try {
            path.putFile(photo).await()
            val photoUrl = path.downloadUrl.await()
            val profileUpdates = userProfileChangeRequest {
                photoUri = Uri.parse(photoUrl.toString())
            }
            user?.updateProfile(profileUpdates)
            FirebaseRDPicture().updatePhoto(FirebaseAuthPicture().getCurrentUser(), photoUrl.toString())
            result = true
        } catch (e: Exception) {
            result = false
        }
        return result

    }

    suspend fun deleteImageProfile(user: FirebaseUser?): Pair<Boolean, String>{
        var result = (false to "")
        Log.d(TAG, "Enter to deleteImageProfile()")
        val path = reference.child(user?.uid.toString()).child("profileImage.jpg")
        try {
            path.delete().await()
            downloadDefaultImage(user)
            FirebaseRDPicture().updatePhoto(FirebaseAuthPicture().getCurrentUser(), FirebaseStoragePicture().getDefaultImage())
            result = (true to (reference.child("/profile_placeholder.jpg").downloadUrl.await().toString()))
        } catch (e: Exception) {
            result = (false to "")
        }
        return result
    }
}