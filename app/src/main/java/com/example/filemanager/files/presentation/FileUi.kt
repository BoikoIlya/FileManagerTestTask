package com.example.filemanager.files.presentation

import com.example.filemanager.core.ClickListener
import com.example.filemanager.core.LongClickListener
import com.example.filemanager.databinding.FileItemBinding
import javax.inject.Inject

/**
 * Created by HP on 07.05.2023.
 **/
data class FileUi(
    private val name: String,
    private val fileImgId: Int,
    private val dateOfCreation: String,
    private val fileSize: String,
    private val path: String
) {

    fun <T> map(mapper: Mapper<T>): T = mapper.map(name, fileImgId, dateOfCreation, fileSize, path)

    fun map(fileUi: FileUi) = fileUi.path == path

    interface Mapper<T> {
        fun map(
            name: String,
            fileImgId: Int,
            dateOfCreation: String,
            size: String,
            path: String
        ): T
    }

    class ToUiMapper @Inject constructor(
        private val binding: FileItemBinding,
        private val clickListener: ClickListener<String>,
        private val longClickListener: LongClickListener<String>
    ): Mapper<Unit>{

        override fun map(
            name: String,
            fileImgId: Int,
            dateOfCreation: String,
            size: String,
            path: String,
        ) = with(binding){
            fileName.text = name
            fileImg.setImageResource(fileImgId)
            fileCreationDate.text = dateOfCreation
            fileSize.text = size
            root.setOnClickListener{
                clickListener.onClick(path)
            }
            root.setOnLongClickListener {
                longClickListener.onLongClick(path)
                return@setOnLongClickListener true
            }
        }

    }

}
