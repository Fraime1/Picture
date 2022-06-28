package com.fraime.android.picture.presentation.di

import com.fraime.android.picture.data.firebase.FirebaseAuthPicture
import com.fraime.android.picture.data.firebase.FirebaseRDPicture
import com.fraime.android.picture.data.firebase.FirebaseStoragePicture
import com.fraime.android.picture.data.repository.*
import com.fraime.android.picture.domain.repository.*
import org.koin.dsl.module
import kotlin.math.sign

val dataModule = module {
    single<PictureRepository> {
        PictureRepositoryImpl(
            firebaseAuthPicture = get(),
            firebaseRDPicture = get())
    }

    single<ChatRepository> {
        ChatRepositoryImpl()
    }
    single<LoginRepository> {
        LoginRepositoryImpl(firebaseAuthPicture = get())
    }

    single<ProfileRepository> {
        ProfileRepositoryImpl(
            firebaseAuthPicture = get(),
            firebaseStoragePicture = get(),
            firebaseRDPicture = get()
        )
    }

    single<SignUpRepository> {
        SignUpRepositoryImpl(firebaseAuthPicture = get())
    }

    single {
        FirebaseAuthPicture()
    }
    single {
        FirebaseStoragePicture()
    }
    single {
        FirebaseRDPicture()
    }

}