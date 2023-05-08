package com.example.filemanager.main.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
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
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.filemanager.R
import com.example.filemanager.core.DataTransfer
import com.example.filemanager.core.ManagerResource
import com.example.filemanager.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var transfer: DataTransfer<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHost.navController
        binding.bottomNavView.setupWithNavController(navController)
        supportActionBar?.title = getString(R.string.app_name)

        lifecycleScope.launch {
            viewModel.collectSingleUiEventCommunication(this@MainActivity){
                it.apply(navController,this@MainActivity, supportFragmentManager,binding)
            }
        }

        if (checkPermission()) {
            //permission allowed
            val path = Environment.getExternalStorageDirectory().path

            val root = File(path)
            val filesAndFolders = Environment.getExternalStorageDirectory().listFiles()
            if(filesAndFolders==null || filesAndFolders.isEmpty()){
                Log.d("tag", "onCreate: no data ")
                return;
            }else{
//                filesAndFolders.forEach {
//                    Log.d("tag", "onCreate: ${it.name}.${it.hashCode()} ")
//                }

            }
        } else {
            //permission not allowed
            requestPermission()
        }

        onBackPressedDispatcher.addCallback(this){
            if(navController.backQueue.size > 2) {
                navController.popBackStack()
            }else{
                viewModel.resetTransfer(Environment.getExternalStorageDirectory().path)
                finish()
            }
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