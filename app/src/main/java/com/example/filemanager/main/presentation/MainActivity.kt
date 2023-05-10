package com.example.filemanager.main.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.filemanager.R
import com.example.filemanager.core.DataCache
import com.example.filemanager.core.PermissionState
import com.example.filemanager.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHost.navController
        binding.bottomNavView.setupWithNavController(navController)


        lifecycleScope.launch {
            viewModel.collectPermissionStateCommunication(this@MainActivity){
                it.apply(this@MainActivity,viewModel)
            }
        }

        lifecycleScope.launch {
            viewModel.collectSingleUiEventCommunication(this@MainActivity){
                it.apply(navController,this@MainActivity, supportFragmentManager,binding)
            }
        }



        onBackPressedDispatcher.addCallback(this){
            if(navController.backQueue.size > 2) {
                viewModel.popCurrentPageListFromStack()
                viewModel.resetTransfer()
                navController.popBackStack()

            }else{
                viewModel.resetTransfer(Environment.getExternalStorageDirectory().path)
                finish()
            }
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            viewModel.permissionState(PermissionState.CheckPermissions)
    }


}