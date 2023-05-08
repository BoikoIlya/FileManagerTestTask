package com.example.filemanager.core


import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by HP on 21.03.2023.
 **/
interface DataTransfer<T> {

    fun read(): T?

    fun save(data: T)

    abstract class Abstract<T>: DataTransfer<T>
    {
        private var data: T? =null

        override fun read(): T? = data

        override fun save(data: T) {
            this.data = data
        }

    }

    class PathTransfer @Inject constructor():
        DataTransfer<String>, DataTransfer.Abstract<String>()




}

