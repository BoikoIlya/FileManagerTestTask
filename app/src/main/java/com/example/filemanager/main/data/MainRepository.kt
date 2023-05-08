package com.example.filemanager.main.data

import com.example.filemanager.core.DataTransfer
import com.example.filemanager.core.TransferRepository
import javax.inject.Inject

/**
 * Created by HP on 08.05.2023.
 **/
interface MainRepository: TransferRepository {

    class Base @Inject constructor(
        transfer: DataTransfer<String>
    ): MainRepository, TransferRepository.Abstract(transfer)
}