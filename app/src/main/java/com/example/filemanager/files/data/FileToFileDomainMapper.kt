package com.example.filemanager.files.data

import com.example.filemanager.core.Mapper
import com.example.filemanager.files.domain.FileDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.io.File
import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributes
import java.util.Stack
import javax.inject.Inject

/**
 * Created by HP on 07.05.2023.
 **/
class FileToFileDomainMapper @Inject constructor(): Mapper<File, FileDomain> {
    
    override suspend fun map(data: File): FileDomain =
             FileDomain(
                name = data.name,
                extension = data.name.substringAfterLast(".", ""),
                dateOfCreation = Files.readAttributes(
                    data.toPath(),
                    BasicFileAttributes::class.java
                )
                    .creationTime().toMillis(),
                size = if(data.isFile) data.length() else 0,
                path = data.path
            )
        }
