package com.fraime.android.picture.presentation.di

import com.fraime.android.picture.presentation.ui.login.LoginViewModel
import com.fraime.android.picture.presentation.ui.messenger.chat.ChatViewModel
import com.fraime.android.picture.presentation.ui.pictureactivity.PictureActivityViewModel
import com.fraime.android.picture.presentation.ui.profile.ProfileViewModel
import com.fraime.android.picture.presentation.ui.profile.bottomsheet.BottomSheetDialogViewModel
import com.fraime.android.picture.presentation.ui.profile.emailchange.EmailChangeViewModel
import com.fraime.android.picture.presentation.ui.profile.passwordchange.PasswordChangeViewModel
import com.fraime.android.picture.presentation.ui.profile.usernamechange.UsernameChangeViewModel
import com.fraime.android.picture.presentation.ui.signup.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var appModule = module {

    viewModel {
        PictureActivityViewModel(
            updateStateUseCase = get()
        )
    }

    viewModel {
        PasswordChangeViewModel(
            changePasswordUseCase = get()
        )
    }

    viewModel<BottomSheetDialogViewModel> {
        BottomSheetDialogViewModel(
            reauthenticateUseCase = get()
        )
    }

    viewModel<EmailChangeViewModel> {
        EmailChangeViewModel(
            changeEmailUseCase = get()
        )
    }

    viewModel<UsernameChangeViewModel> {
        UsernameChangeViewModel(
            changeUsernameUseCase = get()
        )
    }

    viewModel<ChatViewModel> {
        ChatViewModel(
            sendMessageUseCase = get(),
            sendGalleryUseCase = get()
        )
    }

    viewModel<LoginViewModel> {
        LoginViewModel(
            signInWithGoogleUseCase = get(),
            getCurrentUserUseCase = get(),
            signInWithEmailAndPasswordUseCase = get()
        )
    }

    viewModel<ProfileViewModel> {
        ProfileViewModel(
            signOutUseCase = get(),
            getUserUseCase = get(),
            takePhotoUseCase = get(),
            deletePhotoUseCase = get()
        )
    }

    viewModel<SignUpViewModel> {
        SignUpViewModel(
            signUpUseCase = get()
        )
    }
}