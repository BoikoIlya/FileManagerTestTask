package com.example.filemanager.main.data

import android.content.Context
import android.os.Environment
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.filemanager.R
import com.example.filemanager.core.FileDataCache
import com.example.filemanager.core.FilesDao
import com.example.filemanager.core.ManagerResource
import com.example.filemanager.core.Mapper
import com.example.filemanager.files.domain.FileDomain
import com.example.filemanager.files.presentation.FileUi
import com.example.filemanager.files.presentation.FilesState
import com.example.filemanager.lastchangedfiles.presentation.HandledFilesCommunication
import com.example.filemanager.lastchangedfiles.presentation.HandledFilesStateCommunication
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.apache.commons.codec.digest.DigestUtils
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.lang.Exception
import java.util.Stack

/**
 * Created by HP on 09.05.2023.
 **/

@HiltWorker
class SaveFilesHashWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val dao: FilesDao,
    private val handledFilesStateCommunication: HandledFilesStateCommunication,
    private val handledFilesCommunication: HandledFilesCommunication,
    private val toFileUiMapper: FileDomain.Mapper<FileUi>,
    private val toFileDomainMapper: Mapper<File, FileDomain>,
    private val managerResource: ManagerResource
): CoroutineWorker(context, workerParameters) {


    /*
        Here we are fetching allFiles from device storage. Read every file, generate md5 hash,
        and compare with old hash from this file in database. If there is no such file in DB,
        it will be pushed to 'updateFiles' list and then insert to DB. This operation may take
        time, depending on amount of files on your device. In my case it took 2-3 min with 20K
        files with parallel handling with coroutines using await.
     */

    override suspend fun doWork(): Result {
                handledFilesStateCommunication.map(FilesState.FilesHandling(
                    managerResource.getString(R.string.fetching_device_files)))

        val allFilesOnDevice = mutableListOf<File>()
        val stack = Stack<File>()

        stack.push(Environment.getExternalStorageDirectory())

        while (!stack.isEmpty()) {
            val currentDir = stack.pop()
            val files = currentDir.listFiles()

            if (files != null) {
                for (file in files) {
                    if (file.isDirectory) {
                        stack.push(file)
                    } else {
                        allFilesOnDevice.add(file)
                    }
                }
            }
        }
        val updateFiles = emptyList<FileDataCache>().toMutableList()

        val mismatchedFiles: MutableList<File> = mutableListOf()
        val dispatcher = Dispatchers.IO

        coroutineScope {
            var iterationCounter = 0
            val lock = Any() // Create a lock object for synchronization

            allFilesOnDevice.chunked(100).map { chunk ->
                async(dispatcher) {
                    chunk.forEach { file ->
                        try {
                            val fileInputStream = FileInputStream(file)
                            val bufferedInputStream = BufferedInputStream(fileInputStream)
                            val currentHash = DigestUtils.md5Hex(bufferedInputStream)
                            val fileDataCache: FileDataCache? = dao.getFileFiles(file.path)
                            if (fileDataCache != null) {
                                if (currentHash != fileDataCache.hash) {
                                    synchronized(lock) {
                                        mismatchedFiles.add(file)
                                        updateFiles.add(FileDataCache(path = file.path, hash = currentHash))
                                    }
                                }
                            } else {
                                synchronized(lock) {
                                    updateFiles.add(FileDataCache(path = file.path, hash = currentHash))
                                }
                            }

                            if (iterationCounter % 100 == 0) {
                                synchronized(handledFilesStateCommunication) {
                                    handledFilesStateCommunication.map(FilesState.FilesHandling(
                                        managerResource.getString(R.string.find_modifed_file) +
                                                " ${"%.2f".format((iterationCounter.toFloat() / allFilesOnDevice.size.toFloat()) * 100)}%"
                                    ))
                                }
                            }

                            iterationCounter++
                            bufferedInputStream.close()
                            fileInputStream.close()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }.awaitAll()
        }
        handledFilesCommunication.map(
            mismatchedFiles.map { toFileDomainMapper.map(it) }.map { it.map(toFileUiMapper) })

        if(mismatchedFiles.isEmpty()) handledFilesStateCommunication.map(
            FilesState.Failure(managerResource.getString(R.string.nothing_found))
        )
        else handledFilesStateCommunication.map(FilesState.Success)
        dao.insert(updateFiles)
        return Result.success()
    }
}