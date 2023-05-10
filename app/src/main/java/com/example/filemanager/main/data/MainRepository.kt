package com.example.filemanager.main.data

import com.example.filemanager.core.DataCache
import com.example.filemanager.core.TransferRepository
import com.example.filemanager.files.data.cache.PagesFilesCache
import org.apache.commons.codec.digest.DigestUtils
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.lang.Exception
import java.util.Stack
import javax.inject.Inject

/**
 * Created by HP on 08.05.2023.
 **/
interface MainRepository: TransferRepository {

    fun removeCurrentPageList()
    class Base @Inject constructor(
        transfer: DataCache<String>,
        private val pagesCache: PagesFilesCache,
    ): MainRepository, TransferRepository.Abstract(transfer) {
        override fun removeCurrentPageList() = pagesCache.removeCurrentPageList()


    }
}