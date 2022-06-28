package com.fraime.android.picture.presentation.ui.profile.photodialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.fraime.android.picture.R
import com.fraime.android.picture.presentation.ui.profile.ProfileFragment

class PhotoEditDialogFragment(context: Context) : DialogFragment() {
    private val listItems: Array<out String> =
        arrayOf(context.getString(R.string.open_photo), context.getString(R.string.change_photo) ,
            context.getString(R.string.delete_photo))

    private val listener = DialogInterface.OnClickListener { _, which ->
        when (which) {
            0 -> {
                setFragmentResult(ProfileFragment.REQUEST_CODE, bundleOf(RESULT_EDIT to PhotoOpenDialogFragment.PHOTO_OPEN_DIALOG))
            }
            1 -> {
                setFragmentResult(ProfileFragment.REQUEST_CODE, bundleOf(RESULT_EDIT to PhotoChangeDialogFragment.CHANGE_PHOTO_DIALOG))
            }
            2 -> {
                setFragmentResult(ProfileFragment.REQUEST_CODE, bundleOf(RESULT_EDIT to RESULT_EDIT_DELETE_PHOTO))
            }
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setItems(listItems, listener)
            .create()
    }

    companion object {
        const val EDIT_PHOT_DIALOG = "ProgressDialogFragment"
        const val RESULT_EDIT_DELETE_PHOTO = "PhotoEditDialogFragmentDelitePhoto"
        const val RESULT_EDIT = "resultEdit"
    }

}