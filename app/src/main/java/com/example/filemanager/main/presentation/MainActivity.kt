package com.example.filemanager.main.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.filemanager.R
import com.example.filemanager.databinding.ActivityMainBinding
import java.io.File


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHost.navController
        binding.bottomNavView.setupWithNavController(navController)

        if (checkPermission()) {
            //permission allowed
            val path = Environment.getExternalStorageDirectory().path

            val root = File(path)
            val filesAndFolders = root.listFiles()
            if(filesAndFolders==null || filesAndFolders.isEmpty()){
                Log.d("tag", "onCreate: no data ")
                return;
            }else{
                filesAndFolders.forEach {
                    Log.d("tag", "onCreate: ${it.name}.${it.hashCode()} ")
                }

            }
        } else {
            //permission not allowed
            requestPermission()
        }

    }


    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            this@MainActivity,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this@MainActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            Toast.makeText(
                this@MainActivity,
                "Storage permission is requires,please allow from settings",
                Toast.LENGTH_SHORT
            ).show()
        } else ActivityCompat.requestPermissions(
            this@MainActivity,
            arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            111
        )
    }
}