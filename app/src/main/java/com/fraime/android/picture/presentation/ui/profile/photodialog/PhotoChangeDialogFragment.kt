package com.fraime.android.picture.presentation.ui.profile.photodialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.fraime.android.picture.R
import com.fraime.android.picture.presentation.ui.profile.ProfileFragment

private const val TAG = "PhotoChangeDialogFragment"


class PhotoChangeDialogFragment(
    context: Context
) :
    DialogFragment() {
    private val listItems: Array<out String> =
        arrayOf(context.getString(R.string.upload_photo), context.getString(R.string.take_photo))

    private val listener = DialogInterface.OnClickListener { _, which ->
        when (which) {
            0 -> {
                setFragmentResult(ProfileFragment.REQUEST_CODE, bundleOf(RESULT_CHANGE to UPLOAD_FROM_DEVICE))
            }
            1 -> {
                setFragmentResult(ProfileFragment.REQUEST_CODE, bundleOf(RESULT_CHANGE to TAKE_PHOT))
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setItems(listItems, listener)
            .create()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Destroy")
    }

    companion object {
        const val CHANGE_PHOTO_DIALOG = "PhotoChangeDialogFragment"
        const val RESULT_CHANGE = "PhotoChangeDialogFragment"
        const val TAKE_PHOT = "PhotoChangeDialogFragmentTakePhoto"
        const val UPLOAD_FROM_DEVICE = "PhotoChangeDialogFragmentUploadFromDevice"
    }
}