package com.example.filemanager.core

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by HP on 09.05.2023.
 **/
@Database(entities = [FileDataCache::class], version = 1, exportSchema = false)
abstract class FilesDB: RoomDatabase() {

    abstract fun getDao(): FilesDao
}