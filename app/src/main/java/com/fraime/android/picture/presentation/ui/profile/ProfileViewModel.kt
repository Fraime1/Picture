package com.fraime.android.picture.presentation.ui.profile

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.fraime.android.picture.R
import com.fraime.android.picture.domain.model.AppState
import com.fraime.android.picture.domain.model.User
import com.fraime.android.picture.domain.usecase.profile.DeletePhotoUseCase
import com.fraime.android.picture.domain.usecase.profile.GetUserUseCase
import com.fraime.android.picture.domain.usecase.profile.SignOutUseCase
import com.fraime.android.picture.domain.usecase.profile.TakePhotoUseCase
import com.fraime.android.picture.presentation.ui.profile.emailchange.EmailChangeFragment
import com.fraime.android.picture.presentation.ui.profile.usernamechange.UsernameChangeFragment
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "ProfileViewModel"

class ProfileViewModel(
    private val signOutUseCase: SignOutUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val takePhotoUseCase: TakePhotoUseCase,
    private val deletePhotoUseCase: DeletePhotoUseCase
) : ViewModel() {

    val perrmisonsList: Array<out String> =
        arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    lateinit var photoUri: Uri

    private val profileUsername: MutableLiveData<User> = MutableLiveData()

    private val _username: MutableLiveData<String> = MutableLiveData()
    private val _email: MutableLiveData<String> = MutableLiveData()
    private val _photo: MutableLiveData<String> = MutableLiveData()
    private val _state: MutableLiveData<String> = MutableLiveData()
    val username: LiveData<String> = _username
    val email: LiveData<String> = _email
    val photo: LiveData<String> = _photo
    val state: LiveData<String> = _state


    init {
        viewModelScope.launch {
            profileUsername.value = getUserUseCase.execute()
            _username.postValue(profileUsername.value?.name)
            _email.postValue(profileUsername.value?.email)
            _photo.postValue(profileUsername.value?.photo)
            _state.postValue(profileUsername.value?.state)
        }
    }

    fun updatePhoto(photoS: String) {
        _photo.postValue(photoS)
    }

    fun signOut(nvCon: NavController) {
        viewModelScope.launch {
            AppState.updateState(AppState.OFFLINE).await()
            signOutUseCase.execute()
            nvCon.navigate(R.id.action_global_loginFragment)
        }
    }

    fun takePhotoUseCase(photoUri: Uri) {
        viewModelScope.launch {
            val result = takePhotoUseCase.execute(photoUri)
            if (result) {
                updatePhoto(photoUri.toString())
            }
        }
    }

    fun saveImage(context: Context) {
        if (isExternalStorageWritable() && isExternalStorageReadable()) {
            Log.d(TAG, "saveImage")
            val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
            val dt = sdf.format(Date())
            val picturesDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val dir = File(picturesDirectory?.absolutePath, "/PictureApp")
            if (!dir.exists()) {
                dir.mkdir()
            }
            val file = File(dir, "/" + dt + ".jpg")
            photoUri = FileProvider.getUriForFile(
                context,
                "com.fraime.android.picture.fileprovider",
                file
            )
        } else {
            return
        }
    }

    fun deletePhoto() {
        viewModelScope.launch {
            val result = deletePhotoUseCase.execute()
            if (result.first) {
                Log.d(TAG, "URL = ${result.second}")
                updatePhoto(result.second)
            }
        }
    }

    fun toChangeUsername(navController: NavController) {
        navController.navigate(R.id.action_profileFragment_to_usernameChangeFragment,
        bundleOf(UsernameChangeFragment.USERNAME to username.value))
    }

    fun toChangeEmail(navController: NavController) {
        navController.navigate(R.id.action_profileFragment_to_emailChangeFragment,
        bundleOf(EmailChangeFragment.EMAIL to email.value))
    }

    fun toChangePassword(navController: NavController) {
        navController.navigate(R.id.action_profileFragment_to_passwordChangeFragment)
    }

    fun toFriends(navController: NavController) {
        navController.navigate(R.id.action_profileFragment_to_firendsFragment)
    }

    private fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    private fun isExternalStorageReadable(): Boolean {
        return when (Environment.getExternalStorageState()) {
            Environment.MEDIA_MOUNTED,
            Environment.MEDIA_MOUNTED_READ_ONLY,
            -> true
            else -> false
        }
    }

}
