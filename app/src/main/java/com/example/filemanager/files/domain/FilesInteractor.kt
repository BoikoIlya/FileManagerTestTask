package com.example.filemanager.files.domain

import com.example.filemanager.files.data.FilesRepository
import com.example.filemanager.files.presentation.FileUi
import java.io.File
import javax.inject.Inject

/**
 * Created by HP on 07.05.2023.
 **/
interface FilesInteractor {

    suspend fun fetchData(): List<FileUi>

    fun savePath(path: String)


    class Base @Inject constructor(
        private val filesRepository: FilesRepository,
        private val mapper: FileDomain.Mapper<FileUi>
    ): FilesInteractor{

        override suspend fun fetchData(): List<FileUi> = filesRepository.fetchData().map { it.map(mapper) }

        override fun savePath(path: String) = filesRepository.savePath(path)

    }
}