package com.fraime.android.picture.data.firebase

import android.net.Uri
import android.util.Log
import com.fraime.android.picture.domain.model.AppState
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


private const val TAG = "FirebasRDPicture"

class FirebaseRDPicture {
    private val databaseP = Firebase.database

    fun getReference(): DatabaseReference {
        Log.d(TAG, "getReference(): Enter")
        return databaseP.reference
    }

    fun setDefaultUserValue(user: FirebaseUser?, username: String, image: String) {
        val ref = getReference()
        val mapData = mutableMapOf<String, Any>()
        mapData[CHILD_ID] = user?.uid ?: ""
        mapData[CHILD_USERNAME] = username
        mapData[CHILD_EMAIL] = user?.email ?: ""
        mapData[CHILD_PHOTO_URL] = image
        ref.child(NODE_USERS).child(user?.uid ?: "").updateChildren(mapData).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(TAG, "setDefaultUserValue(user: FirebaseUser?) : Success")
            } else {
                Log.d(TAG, "setDefaultUserValue(user: FirebaseUser?) : Failure")
            }
        }
    }

//    suspend fun updateUserData(user: FirebaseUser?, email: String, username: String, photo: String) {
//        val ref = getReference()
//        val mapData = mutableMapOf<String, Any>()
//        mapData[CHILD_EMAIL] = email
//        mapData[CHILD_USERNAME] = username
//        mapData[CHILD_PHOTO_URL] = photo
//        try {
//            ref.child(NODE_USERS).child(user?.uid ?: "").updateChildren(mapData).await()
//            Log.d(TAG, "updateUserData() : Success")
//        } catch (e: Exception) {
//            Log.d(TAG, "updateUserData() : Failure")
//        }
//    }

    suspend fun updateUsername(user: FirebaseUser?, username: String) {
        val ref = getReference()
        val mapData = mutableMapOf<String, Any>()
        mapData[CHILD_USERNAME] = username
        try {
            ref.child(NODE_USERS).child(user?.uid ?: "").updateChildren(mapData).await()
            Log.d(TAG, "updateUserData() : Success")
        } catch (e: Exception) {
            Log.d(TAG, "updateUserData() : Failure")
        }
    }

    suspend fun updateEmail(user: FirebaseUser?, email: String) {
        val ref = getReference()
        val mapData = mutableMapOf<String, Any>()
        mapData[CHILD_EMAIL] = email
        try {
            ref.child(NODE_USERS).child(user?.uid ?: "").updateChildren(mapData).await()
            Log.d(TAG, "updateUserData() : Success")
        } catch (e: Exception) {
            Log.d(TAG, "updateUserData() : Failure")
        }
    }

    suspend fun updatePhoto(user: FirebaseUser?, photo: String) {
        val ref = getReference()
        val mapData = mutableMapOf<String, Any>()
        mapData[CHILD_PHOTO_URL] = photo
        try {
            ref.child(NODE_USERS).child(user?.uid ?: "").updateChildren(mapData).await()
            Log.d(TAG, "updateUserData() : Success")
        } catch (e: Exception) {
            Log.d(TAG, "updateUserData() : Failure")
        }
    }

    suspend fun updateState(appState: AppState, user: FirebaseUser?) {
        val ref = getReference()
        try {
            ref.child(NODE_USERS).child(user?.uid ?: "").child(CHILD_STATE).setValue(appState.state).await()
            Log.d(TAG, "updateState(): Success")
        } catch (e: Exception) {
            Log.d(TAG, "updateState(): Failure")
        }
    }


    companion object {
        const val NODE_USERS = "users"
        const val CHILD_ID = "id"
        const val CHILD_PHOTO_URL = "photo_url"
        const val CHILD_USERNAME = "username"
        const val CHILD_EMAIL = "email"
        const val CHILD_STATE = "state"
    }
}