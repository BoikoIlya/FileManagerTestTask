package com.example.filemanager.files.presentation

import android.os.Environment
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filemanager.R
import com.example.filemanager.core.DispatcherList
import com.example.filemanager.core.ManagerResource
import com.example.filemanager.files.domain.FilesInteractor
import com.example.filemanager.main.presentation.SingleUiEventCommunication
import com.example.filemanager.main.presentation.SingleUiEventState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

/**
 * Created by HP on 07.05.2023.
 **/
@HiltViewModel
class FileViewModel @Inject constructor(
    private val interactor: FilesInteractor,
    private val dispatcherList: DispatcherList,
    private val communication: FilesCommunication,
    private val filesStateCommunication: FilesStateCommunication,
    private val singleUiEventCommunication: SingleUiEventCommunication,
    private val managerResource: ManagerResource
): ViewModel(){



    init {
        fetchFiles()
    }

    fun fetchFiles() = viewModelScope.launch(dispatcherList.io()) {
        filesStateCommunication.map(FilesState.Loading)
        val result = interactor.fetchData()
        communication.map(result)
        filesStateCommunication.map(if(result.isEmpty()) FilesState.NoFiles else FilesState.Success)
    }

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

    suspend fun collectFiles(
        owner: LifecycleOwner,
        collector: FlowCollector<List<FileUi>>
    ) = communication.collect(owner, collector)

    suspend fun collectFilessState(
        owner: LifecycleOwner,
        collector: FlowCollector<FilesState>
    ) = filesStateCommunication.collect(owner, collector)
}