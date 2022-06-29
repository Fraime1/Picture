package com.fraime.android.picture.domain.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import com.fraime.android.picture.domain.model.User
import kotlinx.coroutines.Deferred

interface ProfileRepository {

    fun signOut()

    suspend fun getUser() : User

    suspend fun changeUsername(newUsername: String, oldUsername: String) : Boolean

    suspend fun changeEmail(email: String) : Boolean

    suspend fun changePassword(password: String) : Boolean

    suspend fun takePhoto(photo: Uri) : Boolean

    suspend fun deletePhoto() : Pair<Boolean, String>

    suspend fun reauthenticate(email: String, password: String) : Boolean
}