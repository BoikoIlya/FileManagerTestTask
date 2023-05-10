package com.example.filemanager.lastchangedfiles.presentation

import com.example.filemanager.core.Communication
import com.example.filemanager.files.presentation.FilesState
import javax.inject.Inject

/**
 * Created by HP on 08.05.2023.
 **/
interface HandledFilesStateCommunication: Communication.Mutable<FilesState> {

    class Base @Inject constructor(): HandledFilesStateCommunication, Communication.UiUpdate<FilesState>(FilesState.Empty)
}


