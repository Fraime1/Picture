package com.fraime.android.picture.presentation.ui.profile.usernamechange

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.fraime.android.picture.R
import com.fraime.android.picture.domain.usecase.profile.ChangeUsernameUseCase
import com.fraime.android.picture.domain.usecase.profile.GetUserUseCase
import com.fraime.android.picture.presentation.ui.utils.PictureValidator
import kotlinx.coroutines.launch

private const val TAG = "UsernameChangeViewModel"

class UsernameChangeViewModel(private val changeUsernameUseCase: ChangeUsernameUseCase) : ViewModel() {

    private val _errorInputText: MutableLiveData<String> = MutableLiveData()
    val errorInputText : LiveData<String> = _errorInputText
    val oldUsername: MutableLiveData<String> = MutableLiveData()
    val username : MutableLiveData<String> = MutableLiveData()


    fun saveUsername(navController: NavController){
        if (validUsername()) {
            viewModelScope.launch {
                Log.d(TAG, "Enter to saveUsername() : ${username.value}")
                if (changeUsernameUseCase.execute(username.value ?: "", oldUsername.value ?: "")) {
                    navController.navigate(R.id.action_usernameChangeFragment_to_profileFragment)
                }
            }
        }
    }

    fun validUsername() : Boolean{
        var valid = true
        if (TextUtils.isEmpty(username.value)) {
            _errorInputText.value = "Required"
            valid = false
        } else if ((username.value?.length ?: 0) < 6) {
            _errorInputText.value = "Username can't less than 6"
            valid = false
        } else if (PictureValidator().isStringDoNotContainSpecialCharacter(username.value)) {
            _errorInputText.value = "It is forbidden to use special characters, except for \"_\""
            valid = false
        }
        return valid
    }
}