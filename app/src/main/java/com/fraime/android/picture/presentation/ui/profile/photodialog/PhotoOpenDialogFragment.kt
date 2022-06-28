package com.fraime.android.picture.presentation.ui.profile.photodialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.fraime.android.picture.R
import com.fraime.android.picture.databinding.FragmentProfilePhotoDialogBinding
import com.squareup.picasso.Picasso

private const val TAG = "PhotoOpenDialogFragment"

class PhotoOpenDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentProfilePhotoDialogBinding
    private lateinit var photoUri : String

//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        return AlertDialog.Builder(requireContext())
//            .setView()
//            .create()
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_photo_dialog, container, false)
        photoUri = arguments?.getString(ARG_PHOTO) ?: ""
        Picasso.get()
            .load(photoUri)
            .placeholder(R.drawable.profile_placeholder)
            .into(binding.photoDialog)
        dialog?.setTitle("efef")
        return binding.root
    }

    companion object {
        val ARG_PHOTO = "argPhotoDialog"
        val PHOTO_OPEN_DIALOG = "PhotoOpenDialog"
        fun newInstance(photo: String) : PhotoOpenDialogFragment {
            val args =Bundle().apply {
                putString(ARG_PHOTO, photo)
            }
            return PhotoOpenDialogFragment().apply {
                arguments = args
            }
        }
    }
}