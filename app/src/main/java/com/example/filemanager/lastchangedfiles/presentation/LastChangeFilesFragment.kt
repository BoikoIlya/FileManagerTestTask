package com.example.filemanager.lastchangedfiles.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.filemanager.R
import com.example.filemanager.core.ClickListener
import com.example.filemanager.core.LongClickListener
import com.example.filemanager.core.OrderOption
import com.example.filemanager.databinding.FragmentFilesBinding
import com.example.filemanager.databinding.FragmentLastChangeFilesBinding
import com.example.filemanager.files.presentation.FileViewModel
import com.example.filemanager.files.presentation.FilesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LastChangeFilesFragment:Fragment(R.layout.fragment_last_change_files){

    private val binding by viewBinding(FragmentLastChangeFilesBinding::bind)


    private val viewModel: LastChangedFilesViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.filesRcv.layoutManager = LinearLayoutManager(requireContext())

        val adapter= FilesAdapter(object : ClickListener<String> {
            override fun onClick(data: String) {
                viewModel.openPath(data)
            }
        },object : LongClickListener<String> {
            override fun onLongClick(data: String) {
                viewModel.shareFile(data)
            }
        })

        binding.filesRcv.adapter = adapter


        lifecycleScope.launch {
            viewModel.collectFiles(this@LastChangeFilesFragment){
                adapter.addItems(it)
                binding.filesRcv.scrollToPosition(0)
            }
        }

        lifecycleScope.launch {
            viewModel.collectFilessState(this@LastChangeFilesFragment){
                it.apply(
                    binding.filesRcv,
                    binding.message,
                    binding.progressFiles
                )
            }
        }
    }


}