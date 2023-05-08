package com.example.filemanager.files.presentation

import com.example.filemanager.core.ClickListener
import com.example.filemanager.databinding.FileItemBinding
import javax.inject.Inject

/**
 * Created by HP on 08.05.2023.
 **/
class ToUiMapper @Inject constructor(
    private val binding: FileItemBinding,
    private val clickListener: ClickListener<String>
): FileUi.Mapper<Unit> {

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
    }

}