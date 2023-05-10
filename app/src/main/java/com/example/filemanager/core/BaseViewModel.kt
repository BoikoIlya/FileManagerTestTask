package com.example.filemanager.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filemanager.R
import com.example.filemanager.files.domain.FilesInteractor
import com.example.filemanager.files.presentation.FileUi
import com.example.filemanager.files.presentation.FilesCommunication
import com.example.filemanager.files.presentation.FilesState
import com.example.filemanager.files.presentation.FilesStateCommunication
import com.example.filemanager.main.presentation.SingleUiEventCommunication
import com.example.filemanager.main.presentation.SingleUiEventState
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import java.io.File

/**
 * Created by HP on 09.05.2023.
 **/
abstract class BaseViewModel(
    private val dispatcherList: DispatcherList,
    private val singleUiEventCommunication: SingleUiEventCommunication,
    private val interactor: FilesInteractor,
    private val managerResource: ManagerResource,
    private val communication: Communication.Mutable<List<FileUi>>,
    private val filesStateCommunication: Communication.Mutable<FilesState>,
): ViewModel() {


    fun openPath(path: String) = viewModelScope.launch(dispatcherList.io()) {
        singleUiEventCommunication.map(
            if(File(path).isFile) SingleUiEventState.OpenFile(path)
            else {
                interactor.savePath(path)
                SingleUiEventState.Navigate(R.id.action_filesFragment_self)
            })
    }

    fun shareFile(path: String) = viewModelScope.launch(dispatcherList.io()) {
        singleUiEventCommunication.map(
            if(File(path).isDirectory)
                SingleUiEventState.ShowSnackBar.Error(managerResource.getString(R.string.cant_share_folder))
            else
                SingleUiEventState.ShareFile(path)
        )
    }

    fun sortBy(orderOption: OrderOption) = viewModelScope.launch(dispatcherList.io()) {
        communication.map(interactor.sortData(orderOption))
    }

    suspend fun collectFiles(
        owner: LifecycleOwner,
        collector: FlowCollector<List<FileUi>>
    ) = communication.collect(owner, collector)

    suspend fun collectFilessState(
        owner: LifecycleOwner,
        collector: FlowCollector<FilesState>
    ) = filesStateCommunication.collect(owner, collector)
}