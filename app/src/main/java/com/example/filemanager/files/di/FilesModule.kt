package com.example.filemanager.files.di

import com.example.filemanager.files.presentation.FilesCommunication
import com.example.filemanager.files.presentation.FilesStateCommunication
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent

/**
 * Created by HP on 07.05.2023.
 **/
@Module
@InstallIn(ViewModelComponent::class)
interface FilesModule {

    @ViewModelScoped
    @Binds
    fun bindFilesCommunication(obj: FilesCommunication.Base): FilesCommunication

    @ViewModelScoped
    @Binds
    fun bindFilesStateCommunication(obj: FilesStateCommunication.Base): FilesStateCommunication

}