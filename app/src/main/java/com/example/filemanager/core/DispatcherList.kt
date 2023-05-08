package com.example.filemanager.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher
import javax.inject.Inject

/**
 * Created by HP on 07.05.2023.
 **/
interface DispatcherList {

    fun ui(): MainCoroutineDispatcher

    fun io(): CoroutineDispatcher

    class Base @Inject constructor(): DispatcherList{

        override fun ui() = Dispatchers.Main

        override fun io() = Dispatchers.IO


    }
}