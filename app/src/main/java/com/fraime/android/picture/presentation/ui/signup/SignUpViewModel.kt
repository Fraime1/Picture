package com.fraime.android.picture.presentation.ui.signup

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.*
import com.fraime.android.picture.domain.usecase.signup.SignUpUseCase
import com.fraime.android.picture.presentation.ui.utils.PictureValidator

private const val TAG = "SignUpViewModel"

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase,
) : ViewModel() {


//    private lateinit var result: LiveData<Pair<Boolean, String>>
////    var _resultSignUp: MutableLiveData<Pair<Boolean, String>> = MutableLiveData()
//////    val resultSignUp: LiveData<Pair<Boolean, String>?> = _resultSignUp

    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val username : MutableLiveData<String> = MutableLiveData()

    private val _errorInputTextEmail: MutableLiveData<String?> = MutableLiveData()
    private val _errorInputTextPassword: MutableLiveData<String?> = MutableLiveData()
    private val _errorInputTextUsername: MutableLiveData<String?> = MutableLiveData()
    val errorInputTextEmail: LiveData<String?> = _errorInputTextEmail
    val errorInputTextPassword: LiveData<String?> = _errorInputTextPassword
    val errorInputTextUsername: LiveData<String?> = _errorInputTextUsername

    val failedTextSignUp: MutableLiveData<String?> = MutableLiveData()


    fun signUp(): LiveData<Pair<Boolean, String>>? {
        if (!validForm()) {
            return null
        } else {
            return signUpUseCase.execute(email.value ?: "", password.value ?: "", username.value ?: "")
        }
    }

    private fun validForm(): Boolean {
        var valid = true
        if (TextUtils.isEmpty(email.value)) {
            _errorInputTextEmail.value = "Required."
            valid = false
        } else if (!PictureValidator().isValidEmail(email.value)){
            _errorInputTextEmail.value = "Invalid email"
            valid = false
        } else {
            _errorInputTextEmail.value = null
        }

        if (TextUtils.isEmpty(password.value)) {
            _errorInputTextPassword.value = "Required."
            valid = false
        } else if ((password.value?.length ?: 0) < 6) {
            _errorInputTextPassword.value = "Password can't less than 6"
            valid = false
        } else if (!PictureValidator().isStringContainNumber(password.value)) {
            _errorInputTextPassword.value = "Required at least 1 digit"
            valid = false
        } else if (!PictureValidator().isStringLowerAndUpperCase(password.value)){
            _errorInputTextPassword.value = "Password must contain upper and lower case letters"
            valid = false
        } else if (!PictureValidator().isStringContainSpecialCharacter(password.value)) {
            _errorInputTextPassword.value = "Password must contain [~!@#\$%^&*]"
            valid = false
        } else {
            _errorInputTextPassword.value = null
        }

        if (TextUtils.isEmpty(username.value)) {
            _errorInputTextUsername.value = "Required."
            valid = false
        } else {
            _errorInputTextUsername.value = null
        }

        return valid
    }

    fun hideKeyboard(context: Context?, view: View) {
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}