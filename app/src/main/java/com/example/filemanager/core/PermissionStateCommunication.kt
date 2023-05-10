package com.example.filemanager.core

import javax.inject.Inject

/**
 * Created by HP on 10.05.2023.
 **/
interface PermissionStateCommunication: Communication.Mutable<PermissionState> {

    class Base @Inject constructor(): PermissionStateCommunication, Communication.UiUpdate<PermissionState>(PermissionState.Empty)
}