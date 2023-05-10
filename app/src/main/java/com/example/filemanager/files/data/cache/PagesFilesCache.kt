package com.example.filemanager.files.data.cache

import com.example.filemanager.core.Mapper
import com.example.filemanager.core.OrderOption
import com.example.filemanager.files.domain.FileDomain
import java.io.File
import java.util.Stack
import javax.inject.Inject

/**
 * Created by HP on 08.05.2023.
 **/
interface PagesFilesCache {

    fun saveCurrentPageList(data: List<File>)

    fun removeCurrentPageList()

   suspend fun fetchSortedList(orderOption: OrderOption): List<FileDomain>

    class Base @Inject constructor(
        private val mapper: Mapper<File, FileDomain>
    ): PagesFilesCache {
        private val stack = Stack<List<File>>()

        init {
            stack.push(emptyList())
        }

        override fun saveCurrentPageList(data: List<File>) {
            stack.push(data)
        }

        override fun removeCurrentPageList() {
            stack.pop()
        }

        override suspend fun fetchSortedList(orderOption: OrderOption): List<FileDomain>
            = orderOption.sort(mapper, stack.last())
    }
}