package com.example.filemanager.files.presentation

import com.example.filemanager.core.Communication
import javax.inject.Inject

/**
 * Created by HP on 07.05.2023.
 **/
interface FilesCommunication: Communication.Mutable<List<FileUi>> {

    class Base @Inject constructor(): FilesCommunication, Communication.UiUpdate<List<FileUi>>(emptyList())
}