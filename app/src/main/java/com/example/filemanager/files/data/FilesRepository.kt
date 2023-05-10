package com.example.filemanager.files.data

import android.os.Environment
import com.example.filemanager.core.DataCache
import com.example.filemanager.core.OrderOption
import com.example.filemanager.core.TransferRepository
import com.example.filemanager.files.data.cache.PagesFilesCache
import com.example.filemanager.files.data.cache.FilesStorageDataSource
import com.example.filemanager.files.domain.FileDomain
import java.io.File
import javax.inject.Inject

/**
 * Created by HP on 07.05.2023.
 **/
interface FilesRepository: TransferRepository {

    suspend fun fetchData(): List<FileDomain>

    suspend fun sortedFilesList(orderOption: OrderOption): List<FileDomain>

    class Base @Inject constructor(
        private val storage: FilesStorageDataSource,
        private val filesCache: PagesFilesCache,
        dataTransfer: DataCache<String>
    ): FilesRepository, TransferRepository.Abstract(dataTransfer) {

        override suspend fun fetchData(): List<FileDomain> {

            val cached = storage.fetchData(
                File(dataTransfer.read()?:Environment.getExternalStorageDirectory().path))
            filesCache.saveCurrentPageList(cached)
            return filesCache.fetchSortedList(OrderOption.ByNameASC)
        }

        override suspend fun sortedFilesList(orderOption: OrderOption): List<FileDomain>
            = filesCache.fetchSortedList(orderOption)

    }
}