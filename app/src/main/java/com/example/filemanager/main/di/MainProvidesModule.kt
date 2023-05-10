package com.example.filemanager.main.di

import android.content.Context
import androidx.room.Room
import com.example.filemanager.core.FilesDB
import com.example.filemanager.core.FilesDao
import com.example.filemanager.main.data.WorkManagerWrapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by HP on 10.05.2023.
 **/
@Module
@InstallIn(SingletonComponent::class)
class MainProvidesModule{

    companion object{
        private const val db_name = "files_db"
    }

    @Singleton
    @Provides
    fun provideDataBase(
        @ApplicationContext context: Context
    ): FilesDB {
        return Room.databaseBuilder(
            context,
            FilesDB::class.java,
            db_name
        ).build()
    }

    @Singleton
    @Provides
    fun provideDao(db: FilesDB): FilesDao = db.getDao()

    @Singleton
    @Provides
    fun provideWorkManagerWrapper(
        @ApplicationContext context: Context
    ): WorkManagerWrapper {
        return WorkManagerWrapper.Base(context)
    }
}
