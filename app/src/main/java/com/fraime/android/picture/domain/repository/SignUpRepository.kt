package com.fraime.android.picture.domain.repository

import androidx.lifecycle.LiveData

interface SignUpRepository {

    fun signUp(email: String, password: String, username: String) : LiveData<Pair<Boolean, String>>

}