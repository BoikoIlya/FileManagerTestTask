package com.example.filemanager.core

/**
 * Created by HP on 07.05.2023.
 **/
interface Mapper<T,R> {

   suspend fun map(data: T): R
}