package com.example.filemanager.core

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.filemanager.files.presentation.FileViewModel
import com.example.filemanager.files.presentation.FilesState
import com.example.filemanager.files.presentation.FilesViewHolder
import com.example.filemanager.main.presentation.MainActivity
import com.example.filemanager.main.presentation.MainViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * Created by HP on 10.05.2023.
 **/
sealed interface PermissionState{

    fun apply(
        context: MainActivity,
        viewModel: MainViewModel
    )

    fun apply(
        viewModel: FileViewModel,
        permissionsGrantMessage: String
    )

    object CheckPermissions: PermissionState{
        override fun apply(context: MainActivity, viewModel: MainViewModel) {
            val readPermissionGranted =  ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            val managePermissionGranted = if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.R) Environment.isExternalStorageManager() else true
            if(readPermissionGranted && managePermissionGranted){
                viewModel.permissionState(PermissionState.Granted)
            }else{
                viewModel.permissionState(PermissionState.Denied)
                if(!readPermissionGranted)
                    ActivityCompat.requestPermissions(context, arrayOf( Manifest.permission.READ_EXTERNAL_STORAGE),100)

                    if(!managePermissionGranted){
                        val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                        intent.addCategory("android.intent.category.DEFAULT")
                        intent.data = Uri.parse(String.format("package:%s",context.applicationContext.packageName))
                       context.startActivityIfNeeded(intent,101)
                    }
            }
        }

        override fun apply(viewModel: FileViewModel,permissionsGrantMessage: String) = Unit
    }


    object Denied : PermissionState{
        override fun apply(
            context: MainActivity,
            viewModel: MainViewModel
        ) = Unit

        override fun apply(viewModel: FileViewModel,permissionsGrantMessage: String){
            viewModel.filesUiState(FilesState.Failure(permissionsGrantMessage))
        }

    }



    object Granted: PermissionState{

        override fun apply(context: MainActivity, viewModel: MainViewModel) = viewModel.startWorkManager()

        override fun apply(viewModel: FileViewModel,permissionsGrantMessage: String) = viewModel.fetchFiles()
    }

    object Empty: PermissionState {
        override fun apply(context: MainActivity, viewModel: MainViewModel) = Unit
        override fun apply(viewModel: FileViewModel,permissionsGrantMessage: String)= Unit
    }
}