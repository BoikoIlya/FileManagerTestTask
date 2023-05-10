package com.example.filemanager.main.presentation

import android.os.Environment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filemanager.core.DispatcherList
import com.example.filemanager.core.PermissionState
import com.example.filemanager.core.PermissionStateCommunication
import com.example.filemanager.main.data.MainRepository
import com.example.filemanager.main.data.WorkManagerWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

/**
 * Created by HP on 08.05.2023.
 **/
@HiltViewModel
class MainViewModel @Inject constructor(
    private val communication: SingleUiEventCommunication,
    private val repository: MainRepository,
    private val workManagerWrapper: WorkManagerWrapper,
    private val permissionStateCommunication: PermissionStateCommunication
    ): ViewModel() {

    init {
        permissionState(PermissionState.CheckPermissions)
    }

    fun permissionState(permissionState: PermissionState) = permissionStateCommunication.map(permissionState)
    fun startWorkManager()  {
        workManagerWrapper.start()
    }
    fun resetTransfer(path: String="") {
        repository.savePath(path.ifEmpty { File(repository.readPath()).parentFile?.path ?: Environment.getExternalStorageDirectory().path })
    }

    fun popCurrentPageListFromStack() = repository.removeCurrentPageList()

    suspend fun collectSingleUiEventCommunication(
        owner: LifecycleOwner,
        collector: FlowCollector<SingleUiEventState>
    ) = communication.collect(owner, collector)

    suspend fun collectPermissionStateCommunication(
        owner: LifecycleOwner,
        collector: FlowCollector<PermissionState>
    ) = permissionStateCommunication.collect(owner, collector)
}