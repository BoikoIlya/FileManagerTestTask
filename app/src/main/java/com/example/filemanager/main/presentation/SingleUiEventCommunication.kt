package com.example.filemanager.main.presentation

import com.example.filemanager.core.Communication
import javax.inject.Inject

/**
 * Created by HP on 08.05.2023.
 **/
interface SingleUiEventCommunication: Communication.MutableSingle<SingleUiEventState> {

    class Base @Inject constructor(): SingleUiEventCommunication, Communication.SingleUiUpdate<SingleUiEventState>()
}