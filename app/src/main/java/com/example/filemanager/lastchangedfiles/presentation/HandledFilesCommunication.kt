package com.example.filemanager.lastchangedfiles.presentation

import com.example.filemanager.core.Communication
import com.example.filemanager.files.presentation.FileUi
import javax.inject.Inject

/**
 * Created by HP on 07.05.2023.
 **/
interface HandledFilesCommunication: Communication.Mutable<List<FileUi>> {

    class Base @Inject constructor(): HandledFilesCommunication, Communication.UiUpdate<List<FileUi>>(emptyList())
}