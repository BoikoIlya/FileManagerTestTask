package com.example.filemanager.files.presentation

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by HP on 08.05.2023.
 **/
sealed interface FilesState{

    fun apply(
        recyclerView: RecyclerView,
        textView: TextView,
        progressBar: ProgressBar
    )

    object Success: FilesState{

        override fun apply(
            recyclerView: RecyclerView,
            textView: TextView,
            progressBar: ProgressBar
        ) {
            progressBar.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            textView.visibility = View.GONE
        }
    }

    data class Failure(
        private val message: String
            ): FilesState{

        override fun apply(
            recyclerView: RecyclerView,
            textView: TextView,
            progressBar: ProgressBar
        ) {
            progressBar.visibility = View.GONE
            recyclerView.visibility = View.GONE
            textView.text = message
            textView.visibility = View.VISIBLE
        }
    }

    object Loading: FilesState{

        override fun apply(
            recyclerView: RecyclerView,
            textView: TextView,
            progressBar: ProgressBar,
        ) {
            recyclerView.visibility = View.GONE
            textView.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }

    }

    object Empty: FilesState {
        override fun apply(recyclerView: RecyclerView, textView: TextView, progressBar: ProgressBar) = Unit
    }

    data class FilesHandling(
        private val message: String
    ): FilesState{
        override fun apply(
            recyclerView: RecyclerView,
            textView: TextView,
            progressBar: ProgressBar,
        ) {
            recyclerView.visibility = View.GONE
            textView.text = message
            textView.visibility = View.VISIBLE
            progressBar.visibility = View.VISIBLE
        }
    }

}