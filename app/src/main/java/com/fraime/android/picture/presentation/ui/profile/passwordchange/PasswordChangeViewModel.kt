package com.fraime.android.picture.presentation.ui.profile.passwordchange

import android.text.TextUtils
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.fraime.android.picture.R
import com.fraime.android.picture.domain.usecase.profile.ChangePasswordUseCase
import com.fraime.android.picture.presentation.ui.profile.bottomsheet.BottomSheetDialog
import com.fraime.android.picture.presentation.ui.utils.PictureValidator
import kotlinx.coroutines.launch

class PasswordChangeViewModel(private val changePasswordUseCase: ChangePasswordUseCase) : ViewModel() {

    private val _errorInputText: MutableLiveData<String> = MutableLiveData()
    val errorInputText : LiveData<String> = _errorInputText
    val password: MutableLiveData<String> = MutableLiveData()


    fun savePassword(navController: NavController, fragmentManager: FragmentManager) {
        viewModelScope.launch {
            if (validForm()) {
                if (changePasswordUseCase.execute(password = password.value ?: "")) {
                    navController.navigate(R.id.action_passwordChangeFragment_to_profileFragment)
                } else {
                    BottomSheetDialog().apply {
                        fragmentManager.setFragmentResultListener(
                            BottomSheetDialog.REQUEST_CODE,
                            this
                        ) {
                                key, bundle ->
                            if (key == BottomSheetDialog.REQUEST_CODE) {
                                val result = bundle.getBoolean(BottomSheetDialog.RESULT_CODE)
                                when (result) {
                                    true -> dismiss()
                                    else -> null
                                }
                            }
                        }
                        show(fragmentManager, BottomSheetDialog.BOTTOM_SHEET_DIALOG)
                    }
                }
            }
        }
    }

    private fun validForm() : Boolean {
        var valid = true
        if (TextUtils.isEmpty(password.value)) {
            _errorInputText.value = "Required."
            valid = false
        } else if ((password.value?.length ?: 0) < 6) {
            _errorInputText.value = "Password can't less than 6"
            valid = false
        } else if (!PictureValidator().isStringContainNumber(password.value)) {
            _errorInputText.value = "Required at least 1 digit"
            valid = false
        } else if (!PictureValidator().isStringLowerAndUpperCase(password.value)){
            _errorInputText.value = "Password must contain upper and lower case letters"
            valid = false
        } else if (!PictureValidator().isStringContainSpecialCharacter(password.value)) {
            _errorInputText.value = "Password must contain [~!@#\$%^&*]"
            valid = false
        } else {
            _errorInputText.value = null
        }
        return valid
    }


}