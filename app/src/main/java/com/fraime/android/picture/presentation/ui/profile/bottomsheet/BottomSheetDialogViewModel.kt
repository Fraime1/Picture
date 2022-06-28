package com.fraime.android.picture.presentation.ui.profile.bottomsheet

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fraime.android.picture.domain.usecase.profile.ReauthenticateUseCase
import com.fraime.android.picture.presentation.ui.utils.PictureValidator
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class BottomSheetDialogViewModel(private val reauthenticateUseCase: ReauthenticateUseCase) :
    ViewModel() {

    private val _errorTextInputEmail : MutableLiveData<String?> = MutableLiveData()
    private val _errorTextInputPassword : MutableLiveData<String?> = MutableLiveData()
    val errorTextInputEmail : LiveData<String?> = _errorTextInputEmail
    val errorTextInputPassword : LiveData<String?> = _errorTextInputPassword

    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    suspend fun reauthenticate(): Boolean {
        if (validForm()) {
            return viewModelScope.async {
                (reauthenticateUseCase.execute(email.value ?: "", password.value ?: ""))
            }.await()
        } else {
            return false
        }
    }

    private fun validForm() : Boolean {
        var valid = true
        if (TextUtils.isEmpty(email.value)) {
            _errorTextInputEmail.value = "Required."
            valid = false
        } else if (!PictureValidator().isValidEmail(email.value)){
            _errorTextInputEmail.value = "Invalid email"
            valid = false
        } else {
            _errorTextInputEmail.value = null
        }

        if (TextUtils.isEmpty(password.value)) {
            _errorTextInputPassword.value = "Required."
            valid = false
        } else if ((password.value?.length ?: 0) < 6) {
            _errorTextInputPassword.value = "Password can't less than 6"
            valid = false
        } else if (!PictureValidator().isStringContainNumber(password.value)) {
            _errorTextInputPassword.value = "Required at least 1 digit"
            valid = false
        } else if (!PictureValidator().isStringLowerAndUpperCase(password.value)){
            _errorTextInputPassword.value = "Password must contain upper and lower case letters"
            valid = false
        } else if (!PictureValidator().isStringContainSpecialCharacter(password.value)) {
            _errorTextInputPassword.value = "Password must contain [~!@#\$%^&*]"
            valid = false
        } else {
            _errorTextInputPassword.value = null
        }
        return valid
    }

}