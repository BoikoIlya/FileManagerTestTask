package com.example.filemanager.files.data.cache

import android.os.Environment
import java.io.File
import javax.inject.Inject

/**
 * Created by HP on 07.05.2023.
 **/
interface FilesCacheDataSource {

    suspend fun fetchData(path: File): List<File>

    class Base @Inject constructor(): FilesCacheDataSource{

        override suspend fun fetchData(path: File): List<File> = path.listFiles()?.toList()?: emptyList()

    }
}