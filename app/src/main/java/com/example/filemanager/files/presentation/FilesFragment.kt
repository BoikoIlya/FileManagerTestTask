package com.example.filemanager.files.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.filemanager.R
import com.example.filemanager.core.ClickListener
import com.example.filemanager.core.LongClickListener
import com.example.filemanager.databinding.FragmentFilesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class FilesFragment: Fragment(R.layout.fragment_files){

    private val binding by viewBinding(FragmentFilesBinding::bind)

    private val viewModel: FileViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("tag", "onViewCreated: FRAGMET STACK  ${findNavController().backQueue.size} ")

        binding.filesRcv.layoutManager = LinearLayoutManager(requireContext())

        val adapter= FilesAdapter(object : ClickListener<String>{
            override fun onClick(data: String) {
                viewModel.openPath(data)
            }
        },object : LongClickListener<String>{
            override fun onLongClick(data: String) {
                viewModel.shareFile(data)
            }
        })

        binding.filesRcv.adapter = adapter

        lifecycleScope.launch {
            viewModel.collectFiles(this@FilesFragment){
                adapter.addItems(it)
            }
        }

        lifecycleScope.launch {
            viewModel.collectFilessState(this@FilesFragment){
                it.apply(
                    binding.filesRcv,
                    binding.message,
                    binding.progressFiles
                )
            }
        }
    }
}