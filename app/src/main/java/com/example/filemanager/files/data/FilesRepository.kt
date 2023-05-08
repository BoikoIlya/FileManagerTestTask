package com.example.filemanager.files.data

import android.os.Environment
import com.example.filemanager.core.DataTransfer
import com.example.filemanager.core.Mapper
import com.example.filemanager.core.TransferRepository
import com.example.filemanager.files.data.cache.FilesCacheDataSource
import com.example.filemanager.files.domain.FileDomain
import java.io.File
import javax.inject.Inject

/**
 * Created by HP on 07.05.2023.
 **/
interface FilesRepository: TransferRepository {

    suspend fun fetchData(): List<FileDomain>

    class Base @Inject constructor(
        private val cacheFiles: FilesCacheDataSource,
        private val mapper: Mapper<File, FileDomain>,
        dataTransfer: DataTransfer<String>
    ): FilesRepository, TransferRepository.Abstract(dataTransfer) {

        override suspend fun fetchData(): List<FileDomain> {
            val cached = cacheFiles.fetchData(
                File(dataTransfer.read()?:Environment.getExternalStorageDirectory().path))
            return cached.map { mapper.map(it) }
        }

    }
}