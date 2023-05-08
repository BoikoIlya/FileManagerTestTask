package com.example.filemanager.files.domain

import javax.inject.Inject

/**
 * Created by HP on 07.05.2023.
 **/
interface FileSizeHandler {

    fun handle(size: Long): String

    class Base @Inject constructor(): FileSizeHandler{
        companion object{
            private const val kilo_byte: Long = 1024
            private const val mega_byte: Long = kilo_byte * 1024
            private const val giga_byte: Long = mega_byte * 1024
        }

        override fun handle(size: Long): String {
            return if (size < kilo_byte) "$size B"
             else if (size < mega_byte) String.format("%.2f KB", size.toFloat() / kilo_byte)
             else if (size < giga_byte) String.format("%.2f MB", size.toFloat() / mega_byte)
             else String.format("%.2f GB", size.toFloat() / giga_byte)

        }

    }
}