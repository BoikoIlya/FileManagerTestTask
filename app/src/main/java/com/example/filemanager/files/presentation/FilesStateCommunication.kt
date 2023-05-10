package com.example.filemanager.files.presentation

import com.example.filemanager.core.Communication
import javax.inject.Inject

/**
 * Created by HP on 08.05.2023.
 **/
interface FilesStateCommunication: Communication.Mutable<FilesState> {

    class Base @Inject constructor(): FilesStateCommunication, Communication.UiUpdate<FilesState>(FilesState.Empty)
}


