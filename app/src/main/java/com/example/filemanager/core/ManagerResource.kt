package com.example.filemanager.core

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface ManagerResource {

    fun getString(@StringRes id: Int): String

    fun getColor(@ColorRes id: Int): Int

    class Base @Inject constructor(
       @ApplicationContext
       private val context: Context
    ): ManagerResource {
        override fun getString(id: Int) = context.getString(id)
        override fun getColor(id: Int): Int = context.getColor(id)

    }

}
