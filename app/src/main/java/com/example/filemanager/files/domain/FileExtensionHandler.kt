package com.example.filemanager.files.domain

import com.example.filemanager.R
import javax.inject.Inject

/**
 * Created by HP on 07.05.2023.
 **/
interface FileExtensionHandler {

    fun handle(extension: String): Int

    class Base @Inject constructor(): FileExtensionHandler{

        companion object{
            private const val TXT = "txt"
            private const val APK = "apk"
            private const val JPG = "jpg"
            private const val JPEG = "jpeg"
            private const val JPG_CAPS = "JPG"
            private const val MP3 = "mp3"
            private const val PDF = "pdf"
            private const val PNG = "png"
            private const val ZIP = "zip"
        }

        override fun handle(extension: String): Int =
             when(extension){
                 TXT-> R.drawable.txt
                 APK-> R.drawable.apk
                 JPG-> R.drawable.jpg
                 JPG_CAPS->R.drawable.jpg
                 JPEG->R.drawable.jpg
                 MP3-> R.drawable.mp3
                 PDF-> R.drawable.pdf
                 PNG-> R.drawable.png
                 ZIP-> R.drawable.zip
                 else -> R.drawable.no_name_file
        }
    }
}