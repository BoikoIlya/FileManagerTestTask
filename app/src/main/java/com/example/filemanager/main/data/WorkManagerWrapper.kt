package com.example.filemanager.main.data

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import javax.inject.Inject

/**
 * Created by HP on 09.05.2023.
 **/
interface WorkManagerWrapper {

   fun start()

   class Base @Inject constructor(
       private val context: Context,
   ): WorkManagerWrapper{
       private val workManager = WorkManager.getInstance(context)

       companion object{
           private const val work_name = "work_name"
       }

       override fun start() {
           val request = OneTimeWorkRequestBuilder<SaveFilesHashWorker>().build()
           workManager.enqueueUniqueWork(
               work_name,
               ExistingWorkPolicy.REPLACE,
               request
           )
       }

   }
}