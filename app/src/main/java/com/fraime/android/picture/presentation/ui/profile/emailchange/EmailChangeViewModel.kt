package com.fraime.android.picture.presentation.ui.profile.emailchange

import android.text.TextUtils
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.fraime.android.picture.R
import com.fraime.android.picture.domain.usecase.profile.ChangeEmailUseCase
import com.fraime.android.picture.presentation.ui.profile.bottomsheet.BottomSheetDialog
import com.fraime.android.picture.presentation.ui.utils.PictureValidator
import kotlinx.coroutines.launch

class EmailChangeViewModel(private val changeEmailUseCase : ChangeEmailUseCase) : ViewModel() {

    private val _errorInputText: MutableLiveData<String> = MutableLiveData()
    val errorInputText : LiveData<String> = _errorInputText
    val email : MutableLiveData<String> = MutableLiveData()


    fun saveEmail(navController: NavController, fragmentManager: FragmentManager) {
        viewModelScope.launch {
            if (validForm()) {
                if (changeEmailUseCase.execute(email = email.value ?: "")) {
                    navController.navigate(R.id.action_emailChangeFragment_to_profileFragment)
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

//    private fun hidekeyboard(context: Context, view: View) {
//        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(view.windowToken, 0)
//    }

    private fun validForm() : Boolean{
        var valid = true
        if (TextUtils.isEmpty(email.value)) {
            _errorInputText.value = "Required."
            valid = false
        } else if (!PictureValidator().isValidEmail(email.value)){
            _errorInputText.value = "Invalid email"
            valid = false
        } else {
            _errorInputText.value = null
        }
        return valid
    }
}