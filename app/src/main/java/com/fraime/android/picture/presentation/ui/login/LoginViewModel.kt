package com.fraime.android.picture.presentation.ui.login

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.fraime.android.picture.R
import com.fraime.android.picture.domain.usecase.login.GetCurrentUserUseCase
import com.fraime.android.picture.domain.usecase.login.SignInWithEmailAndPasswordUseCase
import com.fraime.android.picture.domain.usecase.login.SignInWithGoogleUseCase
import com.google.firebase.auth.FirebaseUser

class LoginViewModel(
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val signInWithEmailAndPasswordUseCase: SignInWithEmailAndPasswordUseCase,
) : ViewModel() {

    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    private val _errorInputTextEmail: MutableLiveData<String?> = MutableLiveData()
    private val _errorInputTextPassword: MutableLiveData<String?> = MutableLiveData()
    val errorInputTextEmail: LiveData<String?> = _errorInputTextEmail
    val errorInputTextPassword: LiveData<String?> = _errorInputTextPassword

    val failedSignInText : MutableLiveData<String?> = MutableLiveData()

    private fun getCurrentUser(): FirebaseUser? {
        return getCurrentUserUseCase.execute()
    }

    fun signInWithGoogle() {
        signInWithGoogleUseCase.execute()
    }

    fun checkCurrentUser(navController: NavController) {
        val currentUser = getCurrentUser()
        if (currentUser != null) {
            navController.navigate(
                R.id.action_loginFragment_to_navigation
            )
        }
    }

    fun hideKeyboard(context: Context?, view: View) {
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun signIn() : LiveData<Pair<Boolean, String>>?{
        if (!validForm()) {
            return null
        } else {
            return signInWithEmailAndPasswordUseCase.execute(email.value ?: "", password.value ?: "")
        }
    }

    private fun validForm(): Boolean {
        var valid = true
        if (TextUtils.isEmpty(email.value)) {
            _errorInputTextEmail.value = "Required."
            valid = false
        } else {
            _errorInputTextEmail.value = null
        }

        if (TextUtils.isEmpty(password.value)) {
            _errorInputTextPassword.value = "Required."
            valid = false
        } else {
            _errorInputTextPassword.value = null
        }

        return valid
    }


}