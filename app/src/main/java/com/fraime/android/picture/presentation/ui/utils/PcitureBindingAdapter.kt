package com.fraime.android.picture.presentation.ui.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.fraime.android.picture.R
import com.squareup.picasso.Picasso


@BindingAdapter("app:setImageUri")
fun setImageUri(view: ImageView, imageUri: String?) {
    if (imageUri == null) {
        view.setImageURI(null)
    } else
        Picasso.get()
            .load(imageUri)
            .placeholder(R.drawable.profile_placeholder)
            .into(view)
}