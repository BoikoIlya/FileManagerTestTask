package com.example.filemanager.files.data.cache

import java.io.File
import javax.inject.Inject

/**
 * Created by HP on 07.05.2023.
 **/
interface FilesStorageDataSource {

    suspend fun fetchData(path: File): List<File>

    class Base @Inject constructor(): FilesStorageDataSource{

        override suspend fun fetchData(path: File): List<File> = path.listFiles()?.toList()?: emptyList()

    }
}