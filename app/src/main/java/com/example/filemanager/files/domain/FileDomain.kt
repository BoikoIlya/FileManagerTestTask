package com.example.filemanager.files.domain

import com.example.filemanager.R
import com.example.filemanager.files.presentation.FileUi
import java.io.File
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

/**
 * Created by HP on 07.05.2023.
 **/
data class FileDomain(
    private val name: String,
    private val extension: String,
    private val dateOfCreation: Long,
    private val size: Long,
    private val path: String
){

    fun <T> map(mapper: Mapper<T>): T = mapper.map(name, extension, dateOfCreation, size, path)

    interface Mapper<T>{
        fun map(
            name: String,
            extension: String,
            dateOfCreation: Long,
            size: Long,
            path: String
        ): T
    }

    class ToFileUiMapper @Inject constructor(
        private val fileExtensionHandler: FileExtensionHandler,
        private val fileSizeHandler: FileSizeHandler
    ): Mapper<FileUi>{

        override fun map(
            name: String,
            extension: String,
            dateOfCreation: Long,
            size: Long,
            path: String,
        ): FileUi {
            return FileUi(
                name = name,
                fileImgId = if(File(path).isDirectory) R.drawable.folder else fileExtensionHandler.handle(extension),
                dateOfCreation = LocalDateTime.ofInstant(Instant.ofEpochMilli(dateOfCreation), ZoneId.systemDefault())
                    .format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                fileSize = if(size!=0L) fileSizeHandler.handle(size) else "",
                path = path
            )
        }

    }

    class CompareByTimeMapper : Mapper<Comparator<FileDomain>>{
        override fun map(
            name: String,
            extension: String,
            dateOfCreation: Long,
            size: Long,
            path: String
        ): Comparator<FileDomain> = Comparator { first, second ->
            when {
                first.dateOfCreation > second.dateOfCreation -> 1
                first.dateOfCreation < second.dateOfCreation -> -1
                else -> 0
            }
        }
    }

}
