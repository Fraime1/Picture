package com.fraime.android.picture.data.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fraime.android.picture.domain.model.AppState
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

private const val TAG = "FirebaseAuthPicture"

class FirebaseAuthPicture {
    private val auth = Firebase.auth

    fun getCurrentUser(): FirebaseUser? {
        Log.d(TAG, "CurrentUser=${auth.currentUser}")
        return auth.currentUser
    }

    fun signUpWithEmailAndPassword(
        email: String,
        password: String,
        username: String,
    ): LiveData<Pair<Boolean, String>> {
        val resultMutableLiveData: MutableLiveData<Pair<Boolean, String>> = MutableLiveData()
        Log.d(TAG, "Enter Sign Up with Email and Password")
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //Update Auth profile data
                    val profileUpdates = userProfileChangeRequest {
                        displayName = username
                    }
                    FirebaseStoragePicture().downloadDefaultImage(getCurrentUser())
                    auth.currentUser?.updateProfile(profileUpdates)?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "Username = ${auth.currentUser?.displayName}")
                        }
                    }
                    val emailVerified = auth.currentUser?.isEmailVerified
                    if (emailVerified != null) {
                        if (!emailVerified) {
                            auth.currentUser?.sendEmailVerification()
                                ?.addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Log.d(TAG, "Email sent")
                                    }
                                }
                        }
                    }
                    //Create database node with new current user and node with friends
                    CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
                        FirebaseRDPicture().setDefaultUserValue(getCurrentUser(), username, FirebaseStoragePicture().getDefaultImage()).await()
                        FirebaseRDPicture().setDefaultFriendValue(getCurrentUser(),username).await()
                        AppState.updateState(AppState.ONLINE).await()
                        Log.d(TAG, "signUpWithEmail: success")
                        resultMutableLiveData.postValue((true to ""))
                    }
                } else {
                    Log.d(TAG, "signUpWithEmail: failure")
                    resultMutableLiveData.value =
                        (false to task.exception.toString().split(":").last())
                    Log.d(TAG, "${resultMutableLiveData.value?.second}")
                }
            }
        return resultMutableLiveData
    }

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
    ): LiveData<Pair<Boolean, String>> {
        val resultMutableLiveData: MutableLiveData<Pair<Boolean, String>> = MutableLiveData()
        Log.d(TAG, "Enter Sign In with Email and Password")
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail: success")
                    resultMutableLiveData.value = (true to "")
                } else {
                    Log.d(TAG, "signInWithEmail: failure", task.exception)
                    resultMutableLiveData.value =
                        (false to task.exception.toString().split(":").last())
                }
            }
        return resultMutableLiveData
    }

    fun signOut() {
        auth.signOut()
    }

    suspend fun changeUsername(newUsername: String, oldUsername: String): Boolean {
        val user = getCurrentUser()
        val userChangeProfile = userProfileChangeRequest {
            displayName = newUsername
        }
        return try {
            user?.updateProfile(userChangeProfile)?.await()
            FirebaseRDPicture().updateUsername(getCurrentUser(), newUsername, oldUsername)
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun changeEmail(email: String): Boolean {
        val user = getCurrentUser()
        try {
            user?.updateEmail(email)?.await()
            FirebaseRDPicture().updateEmail(getCurrentUser(), email)
            Log.d(TAG, "changeEmail() : Success")
            return true
        } catch (e: Exception) {
            if (e is FirebaseAuthRecentLoginRequiredException) {
                Log.d(TAG, "changeEmail(): Failure - FirebaseAuthRecentLoginRequiredException")
                return false
            }
            return false
        }
    }

    suspend fun reauthenticate(email: String, password: String): Boolean {
        val user = getCurrentUser()
        return try {
            val credential = EmailAuthProvider.getCredential(email, password)
            user?.reauthenticate(credential)?.await()
            Log.d(TAG, "reauthenticate() : Success")
            true
        } catch (e: Exception) {
            Log.d(TAG, "reauthenticate() : Failure")
            false
        }
    }

    suspend fun changePassword(password: String) : Boolean {
        val user = getCurrentUser()
        return try {
            user?.updatePassword(password)?.await()
            Log.d(TAG, "changePassword() : Success")
            return true
        } catch (e: Exception) {
            if (e is FirebaseAuthRecentLoginRequiredException) {
                Log.d(TAG, "changeEmail(): Failure - FirebaseAuthRecentLoginRequiredException")
                return false
            }
            return false
        }
    }
}