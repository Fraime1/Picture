package com.fraime.android.picture.presentation.ui.profile

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fraime.android.picture.databinding.FragmentProfileBinding
import com.fraime.android.picture.presentation.ui.profile.photodialog.PhotoChangeDialogFragment
import com.fraime.android.picture.presentation.ui.profile.photodialog.PhotoEditDialogFragment
import com.fraime.android.picture.presentation.ui.profile.photodialog.PhotoOpenDialogFragment

import org.koin.androidx.viewmodel.ext.android.viewModel


private const val TAG = "ProfileFragment"

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel by viewModel<ProfileViewModel>()

    private val camera = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            Log.d(TAG, "Take Camera")
            binding.viewModel?.photoUri?.let { ptUri ->
                Log.d(TAG, "photoUri equals $ptUri")
                binding.viewModel?.takePhotoUseCase(ptUri)
            }
        }
        if (!it) {
            Log.d(TAG, "Don't enter")
        }
    }

    private val vsMedia = registerForActivityResult(ActivityResultContracts.GetContent()) {
        Log.d(TAG, "Enter to vsMedia")
        binding.viewModel?.takePhotoUseCase(it)
    }

    private val cameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            when {
                (it[Manifest.permission.CAMERA] == true) && (it[Manifest.permission.WRITE_EXTERNAL_STORAGE] == true) -> {
                    Log.d(TAG, "Permissions have been granted")
                    Log.d(TAG, "Permissions' photoUri = ${binding.viewModel?.photoUri}")
                    camera.launch(binding.viewModel?.photoUri)
                }
                !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {

                }
                else -> {

                }
            }
        }

    private val readExternalPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            when {
                granted -> {
                    vsMedia.launch("image/*")
                }
                !shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {

                }
                else -> {

                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "Enter to onCreateView()")
        binding = FragmentProfileBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "Enter to onViewCreated()")
        binding.signOutButton.setOnClickListener{
            viewModel.signOut(findNavController())
        }
        binding.profilePhoto.setOnClickListener{
            binding.viewModel?.saveImage(requireActivity())
            PhotoEditDialogFragment(requireContext()).apply {
                this@ProfileFragment.childFragmentManager.setFragmentResultListener(
                    REQUEST_CODE,
                    this@ProfileFragment
                ) {
                        requestKey,bundle ->
                    if (requestKey == REQUEST_CODE){
                        val result = bundle.getString(PhotoEditDialogFragment.RESULT_EDIT)
                        when (result) {
                            //Open PhotoChangeDialog
                            PhotoChangeDialogFragment.CHANGE_PHOTO_DIALOG -> {
                                PhotoChangeDialogFragment(requireContext()).apply {
                                    this@ProfileFragment.childFragmentManager.setFragmentResultListener(
                                        REQUEST_CODE,
                                        this@ProfileFragment
                                    ) {
                                            requestKey, bundle ->
                                        if (requestKey == REQUEST_CODE) {
                                            val result = bundle.getString(PhotoChangeDialogFragment.RESULT_CHANGE)
                                            when (result) {
                                                PhotoChangeDialogFragment.TAKE_PHOT -> {
                                                    if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {

                                                    }else {
                                                        cameraPermission.launch(viewModel.perrmisonsList)
                                                    }
                                                }
                                                PhotoChangeDialogFragment.UPLOAD_FROM_DEVICE -> {
                                                    if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {

                                                    }else {
                                                        readExternalPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                                                    }
                                                }
                                            }

                                        }
                                    }
                                    show(this@ProfileFragment.childFragmentManager, PhotoChangeDialogFragment.CHANGE_PHOTO_DIALOG)
                                }
                            }
                            PhotoOpenDialogFragment.PHOTO_OPEN_DIALOG -> {
                                PhotoOpenDialogFragment.newInstance(binding.viewModel?.photo?.value ?: "")
                                    .show(this@ProfileFragment.childFragmentManager, REQUEST_CODE)
                            }
                            PhotoEditDialogFragment.RESULT_EDIT_DELETE_PHOTO -> {
                                viewModel.deletePhoto()
                            }
                        }
                    }
                }
                show(this@ProfileFragment.childFragmentManager, PhotoEditDialogFragment.EDIT_PHOT_DIALOG)
            }

        }


        binding.usernameLinear.setOnClickListener {
            viewModel.toChangeUsername(findNavController())
        }

        binding.emailLinear.setOnClickListener {
            viewModel.toChangeEmail(findNavController())
        }

        binding.passwordLinear.setOnClickListener {
            viewModel.toChangePassword(findNavController())
        }
        binding.firendsLinear.setOnClickListener {
            viewModel.toFriends(findNavController())
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "Enter to onStart()")
    }

    companion object {
        const val REQUEST_CODE = "profile_fragment_resuest_code"
    }

}