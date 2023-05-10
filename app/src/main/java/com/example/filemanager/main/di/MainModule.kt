package com.example.filemanager.main.di

import android.content.Context
import androidx.room.Room
import com.example.filemanager.core.DataCache
import com.example.filemanager.core.DispatcherList
import com.example.filemanager.core.FileDataCache
import com.example.filemanager.core.FilesDB
import com.example.filemanager.core.FilesDao
import com.example.filemanager.core.ManagerResource
import com.example.filemanager.core.Mapper
import com.example.filemanager.core.PermissionStateCommunication

import com.example.filemanager.files.data.FileToFileDomainMapper
import com.example.filemanager.files.data.FilesRepository
import com.example.filemanager.files.data.cache.PagesFilesCache
import com.example.filemanager.files.data.cache.FilesStorageDataSource
import com.example.filemanager.files.domain.FileDomain
import com.example.filemanager.files.domain.FileExtensionHandler
import com.example.filemanager.files.domain.FileSizeHandler
import com.example.filemanager.files.domain.FilesInteractor
import com.example.filemanager.files.presentation.FileUi
import com.example.filemanager.files.presentation.FilesCommunication
import com.example.filemanager.files.presentation.FilesStateCommunication
import com.example.filemanager.lastchangedfiles.presentation.HandledFilesCommunication
import com.example.filemanager.lastchangedfiles.presentation.HandledFilesStateCommunication
import com.example.filemanager.main.data.MainRepository
import com.example.filemanager.main.data.SaveFilesHashWorker
import com.example.filemanager.main.data.WorkManagerWrapper
import com.example.filemanager.main.presentation.SingleUiEventCommunication
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import java.io.File
import javax.inject.Singleton

/**
 * Created by HP on 07.05.2023.
 **/
@Module
@InstallIn(SingletonComponent::class)
interface MainModule {

    @Singleton
    @Binds
    fun bindFilesRepository(obj: FilesRepository.Base): FilesRepository

    @Singleton
    @Binds
    fun bindFilesInteractor(obj: FilesInteractor.Base): FilesInteractor

    @Singleton
    @Binds
    fun bindFileExtensionHandler(obj: FileExtensionHandler.Base): FileExtensionHandler

    @Singleton
    @Binds
    fun bindFileSizeHandler(obj: FileSizeHandler.Base): FileSizeHandler

    @Singleton
    @Binds
    fun bindFileToFileDomainMapper(obj: FileToFileDomainMapper): Mapper<File, FileDomain>


    @Singleton
    @Binds
    fun bindToFileUiMapper(obj: FileDomain.ToFileUiMapper): FileDomain.Mapper<FileUi>

    @Singleton
    @Binds
    fun bindToUiMapper(obj: FileUi.ToUiMapper): FileUi.Mapper<Unit>

    @Singleton
    @Binds
    fun bindDispatcherList(obj: DispatcherList.Base): DispatcherList

    @Singleton
    @Binds
    fun bindFilesCacheDataSource(obj: FilesStorageDataSource.Base): FilesStorageDataSource


    @Singleton
    @Binds
    fun bindPathTransfer(obj: DataCache.PathTransfer): DataCache<String>

    @Singleton
    @Binds
    fun bindSingleUiEventCommunication(obj: SingleUiEventCommunication.Base): SingleUiEventCommunication

    @Binds
    @Singleton
    fun bindManagerResource(managerResource: ManagerResource.Base): ManagerResource

    @Binds
    @Singleton
    fun bindFilesCache(filesCache: PagesFilesCache.Base): PagesFilesCache

    @Binds
    @Singleton
    fun bindMainRepository(mainRepository: MainRepository.Base): MainRepository


    @Singleton
    @Binds
    fun bindHandledFilesCommunication(obj: HandledFilesCommunication.Base): HandledFilesCommunication

    @Singleton
    @Binds
    fun bindPermissionStateCommunication(obj: PermissionStateCommunication.Base): PermissionStateCommunication

    @Singleton
    @Binds
    fun bindHandledFilesStateCommunication(obj: HandledFilesStateCommunication.Base): HandledFilesStateCommunication


}
