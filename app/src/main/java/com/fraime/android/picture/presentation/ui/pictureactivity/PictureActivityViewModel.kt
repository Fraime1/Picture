package com.fraime.android.picture.presentation.ui.pictureactivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fraime.android.picture.domain.model.AppState
import com.fraime.android.picture.domain.usecase.picture.UpdateStateUseCase
import kotlinx.coroutines.launch

class PictureActivityViewModel(
    private val updateStateUseCase: UpdateStateUseCase
) : ViewModel() {

    fun updateStateOnline() {
        viewModelScope.launch {
            updateStateUseCase.execute(AppState.ONLINE)
        }
    }

    fun updateStateOffline() {
        viewModelScope.launch {
            updateStateUseCase.execute(AppState.OFFLINE)
        }
    }
}