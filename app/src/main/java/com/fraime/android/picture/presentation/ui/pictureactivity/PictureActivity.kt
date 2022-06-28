package com.fraime.android.picture.presentation.ui.pictureactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.fraime.android.picture.R
import com.fraime.android.picture.databinding.ActivityPictureBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class PictureActivity : AppCompatActivity() {
    lateinit var binding: ActivityPictureBinding
    private val viewModel by viewModel<PictureActivityViewModel> ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_picture)
        setContentView(binding.root)


        val navController =
            (supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment).navController

        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.homeFragment,
            R.id.messengerFragment,
            R.id.settingsFragment,
            R.id.profileFragment,
            R.id.loginFragment
        ))

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    override fun onStart() {
        super.onStart()
        viewModel.updateStateOnline()
    }

    override fun onStop() {
        super.onStop()
        viewModel.updateStateOffline()
    }
}