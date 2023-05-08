package com.example.filemanager.main.presentation

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.filemanager.R
import com.example.filemanager.databinding.ActivityMainBinding
import com.example.filemanager.files.presentation.FilesState
import com.google.android.material.snackbar.Snackbar
import java.io.File

/**
 * Created by HP on 08.05.2023.
 **/
sealed interface SingleUiEventState{

    fun apply(
        navController: NavController,
        context: Context,
        fragmentManager: FragmentManager,
        binding: ActivityMainBinding,
    )

    data class OpenFile(
        private val path: String
    ): SingleUiEventState {


        override fun apply(
            navController: NavController,
            context: Context,
            fragmentManager: FragmentManager,
            binding: ActivityMainBinding
        ) {
            val intent = Intent(Intent.ACTION_VIEW)
            val uri = FileProvider.getUriForFile(
                context, context.applicationContext.packageName+".provider", File(path)
            )
            intent.setDataAndType(uri, "*/*")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(intent)
        }

    }

    data class ShareFile(
        private val path: String
    ): SingleUiEventState{
        override fun apply(
            navController: NavController,
            context: Context,
            fragmentManager: FragmentManager,
            binding: ActivityMainBinding
        ) {
            val intent = Intent(Intent.ACTION_SEND)
            val file = File(path)
            val uri = FileProvider.getUriForFile(
                context, context.applicationContext.packageName+".provider", file
            )
            intent.type = "*/*"
            intent.putExtra(Intent.EXTRA_STREAM,uri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(Intent.createChooser(intent, file.name))
        }

    }

    data class Navigate(
        private val destination: Int
    ): SingleUiEventState{

        override fun apply(
            navController: NavController,
            context: Context,
            fragmentManager: FragmentManager,
            binding: ActivityMainBinding,
        ) {
            navController.navigate(destination)
        }
    }

    abstract class ShowSnackBar(
        private val message: String,
        private val bgColorId: Int
    ): SingleUiEventState {

        override fun apply(
            navController: NavController,
            context: Context,
            fragmentManager: FragmentManager,
            binding: ActivityMainBinding,
        ) = with(binding) {
            val snackBar = Snackbar.make(fragmentContainer, message, Snackbar.LENGTH_SHORT)
            snackBar
                .setBackgroundTint(context.getColor(bgColorId))
                .setTextColor(context.getColor(R.color.white))
                .setAnchorView(bottomNavView)
                .show()

        }


        data class Error(
            private val message: String
        ) : ShowSnackBar(message, R.color.red)

        data class Success(
            private val message: String
        ) : ShowSnackBar(message, R.color.green)
    }

}