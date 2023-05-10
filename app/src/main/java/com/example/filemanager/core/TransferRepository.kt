package com.example.filemanager.core

import android.os.Environment

/**
 * Created by HP on 08.05.2023.
 **/
interface TransferRepository {

    fun savePath(path: String)

    fun readPath(): String

    abstract class Abstract(
        protected val dataTransfer: DataCache<String>
    ): TransferRepository{
        override fun savePath(path: String) = dataTransfer.save(path)

        override fun readPath(): String = dataTransfer.read()?:Environment.getExternalStorageDirectory().path
    }
}