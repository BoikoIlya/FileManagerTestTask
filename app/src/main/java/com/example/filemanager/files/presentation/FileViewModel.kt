package com.example.filemanager.files.presentation

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.example.filemanager.R
import com.example.filemanager.core.BaseViewModel
import com.example.filemanager.core.DispatcherList
import com.example.filemanager.core.ManagerResource
import com.example.filemanager.core.PermissionState
import com.example.filemanager.core.PermissionStateCommunication
import com.example.filemanager.files.domain.FilesInteractor
import com.example.filemanager.main.presentation.SingleUiEventCommunication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

/**
 * Created by HP on 07.05.2023.
 **/
@HiltViewModel
class FileViewModel @Inject constructor(
    private val interactor: FilesInteractor,
    private val dispatcherList: DispatcherList,
    private val communication: FilesCommunication,
    private val filesStateCommunication: FilesStateCommunication,
    singleUiEventCommunication: SingleUiEventCommunication,
    private val managerResource: ManagerResource,
    private val permissionStateCommunication: PermissionStateCommunication
): BaseViewModel(
    dispatcherList,
    singleUiEventCommunication,
    interactor,
    managerResource,
    communication,
    filesStateCommunication
){


    fun fetchFiles() {
        viewModelScope.launch(dispatcherList.io()) {
            filesStateCommunication.map(FilesState.Loading)
            val result = interactor.fetchData()
            communication.map(result)
            filesStateCommunication.map(
                if (result.isEmpty()) FilesState.Failure(managerResource.getString(R.string.nothing_found))
                else FilesState.Success
            )
        }
    }

    fun filesUiState(filesUiState: FilesState) = filesStateCommunication.map(filesUiState)

    suspend fun collectPermissionStateCommunication(
        owner: LifecycleOwner,
        collector: FlowCollector<PermissionState>
    ) = permissionStateCommunication.collect(owner, collector)

}