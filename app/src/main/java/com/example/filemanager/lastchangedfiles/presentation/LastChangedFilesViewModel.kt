package com.example.filemanager.lastchangedfiles.presentation

import com.example.filemanager.core.BaseViewModel
import com.example.filemanager.core.DispatcherList
import com.example.filemanager.core.ManagerResource
import com.example.filemanager.files.domain.FilesInteractor
import com.example.filemanager.files.presentation.FilesCommunication
import com.example.filemanager.files.presentation.FilesStateCommunication
import com.example.filemanager.main.presentation.SingleUiEventCommunication
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by HP on 09.05.2023.
 **/
@HiltViewModel
class LastChangedFilesViewModel @Inject constructor(
    interactor: FilesInteractor,
    dispatcherList: DispatcherList,
    communication: HandledFilesCommunication,
    filesStateCommunication: HandledFilesStateCommunication,
    singleUiEventCommunication: SingleUiEventCommunication,
    managerResource: ManagerResource
): BaseViewModel(
    dispatcherList,
    singleUiEventCommunication,
    interactor,
    managerResource,
    communication,
    filesStateCommunication
) {
}