package com.fraime.android.picture.presentation.ui.utils

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.fraime.android.picture.R

class ProgressDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setView(R.layout.fragment_progress_dialog)
            .create()
    }


    companion object{
        val PROGRESS_DIALOG = "ProgressDialogFragment"
    }
}