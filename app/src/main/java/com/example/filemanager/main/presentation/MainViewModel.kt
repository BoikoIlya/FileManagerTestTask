package com.example.filemanager.main.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.example.filemanager.main.data.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

/**
 * Created by HP on 08.05.2023.
 **/
@HiltViewModel
class MainViewModel @Inject constructor(
    private val communication: SingleUiEventCommunication,
    private val repository: MainRepository
): ViewModel() {

    fun resetTransfer(path: String) = repository.savePath(path)

    suspend fun collectSingleUiEventCommunication(
        owner: LifecycleOwner,
        collector: FlowCollector<SingleUiEventState>
    ) = communication.collect(owner, collector)
}