package com.fraime.android.picture.presentation.di


import com.fraime.android.picture.domain.usecase.chat.SendGalleryUseCase
import com.fraime.android.picture.domain.usecase.chat.SendMessageUseCase
import com.fraime.android.picture.domain.usecase.login.GetCurrentUserUseCase
import com.fraime.android.picture.domain.usecase.login.SignInWithEmailAndPasswordUseCase
import com.fraime.android.picture.domain.usecase.login.SignInWithGoogleUseCase
import com.fraime.android.picture.domain.usecase.picture.UpdateStateUseCase
import com.fraime.android.picture.domain.usecase.profile.*
import com.fraime.android.picture.domain.usecase.signup.SignUpUseCase
import org.koin.dsl.module

val domainModule = module {

    factory {
        UpdateStateUseCase(pictureRepository = get())
    }

    factory {
        ChangePasswordUseCase(profileRepository = get())
    }

    factory {
        ReauthenticateUseCase(profileRepository = get())
    }

    factory {
        ChangeEmailUseCase(profileRepository = get())
    }

    factory {
        ChangeUsernameUseCase(profileRepository = get())
    }
    factory {
        DeletePhotoUseCase(profileRepository = get())
    }

    factory {
        TakePhotoUseCase(profileRepository = get())
    }

    factory {
        SendMessageUseCase(chatRepository = get())
    }

    factory {
        GetUserUseCase(profileRepository = get())
    }

    factory {
        SendGalleryUseCase()
    }

    factory {
        SignInWithGoogleUseCase(loginRepository = get())
    }

    factory {
        GetCurrentUserUseCase(loginRepository = get())
    }

    factory {
        SignInWithEmailAndPasswordUseCase(loginRepository = get())
    }

    factory {
        SignOutUseCase(profileRepository = get())
    }

    factory {
        SignUpUseCase(signUpRepository = get())
    }

}