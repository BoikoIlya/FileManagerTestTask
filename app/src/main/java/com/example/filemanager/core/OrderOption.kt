package com.example.filemanager.core

import com.example.filemanager.files.domain.FileDomain
import java.io.File
import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributes

/**
 * Created by HP on 08.05.2023.
 **/
sealed interface OrderOption {

    suspend fun sort(
        mapper: Mapper<File, FileDomain>,
        list: List<File>
    ): List<FileDomain>

    abstract class Abstract(
        private val sorting: suspend (list: List<File>)-> List<File>
    ): OrderOption{

        override suspend fun sort(
            mapper: Mapper<File, FileDomain>,
            list: List<File>
        ): List<FileDomain> = sorting.invoke(list).map { mapper.map(it) }
    }



    object ByTimeASC: Abstract({it.sortedBy {file->
        Files.readAttributes(file.toPath(), BasicFileAttributes::class.java).creationTime().toMillis() }})

    object ByTimeDESC: Abstract({it.sortedByDescending { file->
        Files.readAttributes(file.toPath(), BasicFileAttributes::class.java).creationTime().toMillis() }})

    object ByNameASC: Abstract({list->list.sortedBy { it.name }})

    object ByNameDESC: Abstract({list->list.sortedByDescending { it.name }})

    object BySizeASC: Abstract({list->list.sortedBy { it.length() }})

    object BySizeDESC: Abstract({list->list.sortedByDescending { it.length() }})

    object ByExtensionASC: Abstract({list->list.sortedBy { it.name.substringAfterLast(".", "") }})

    object ByExtensionDESC: Abstract({list->list.sortedByDescending { it.name.substringAfterLast(".", "") }})
}