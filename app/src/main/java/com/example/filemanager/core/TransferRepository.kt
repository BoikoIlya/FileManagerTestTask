package com.example.filemanager.core

/**
 * Created by HP on 08.05.2023.
 **/
interface TransferRepository {

    fun savePath(path: String)

    abstract class Abstract(
        protected val dataTransfer: DataTransfer<String>
    ): TransferRepository{

        override fun savePath(path: String) = dataTransfer.save(path)

    }
}