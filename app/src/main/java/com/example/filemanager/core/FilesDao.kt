package com.example.filemanager.core

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created by HP on 09.05.2023.
 **/
@Dao
interface FilesDao {

    companion object{
        const val FILES_TABLE_NAME = "files_table"
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<FileDataCache>)

    @Query("SELECT * FROM $FILES_TABLE_NAME WHERE path = :path ")
    suspend fun getFileFiles(path: String): FileDataCache?
}