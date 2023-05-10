package com.example.filemanager.core


import com.example.filemanager.files.domain.FileDomain
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by HP on 21.03.2023.
 **/
interface DataCache<T> {

    fun read(): T?

    fun save(data: T)

    abstract class Abstract<T>: DataCache<T>
    {
        protected var data: T? =null

        override fun read(): T? = data

        override fun save(data: T) {
            this.data = data
        }

    }

    class PathTransfer @Inject constructor():
        DataCache<String>, Abstract<String>()

    class ChangedFilesCache @Inject constructor():
            DataCache<List<FileDomain>>, Abstract<List<FileDomain>>()

    @Singleton
    class AllDeviceFilesCache @Inject constructor():
        DataCache<List<File>>, Abstract<List<File>>()
}

