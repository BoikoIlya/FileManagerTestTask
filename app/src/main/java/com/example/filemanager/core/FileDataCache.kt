package com.example.filemanager.core

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by HP on 09.05.2023.
 **/
@Entity(tableName = FilesDao.FILES_TABLE_NAME)
data class FileDataCache(
    @PrimaryKey(autoGenerate = false)
    val path: String,
    val hash: String,

)
