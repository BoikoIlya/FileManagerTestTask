package com.example.filemanager.files.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.filemanager.R
import com.example.filemanager.core.ClickListener
import com.example.filemanager.core.LongClickListener
import com.example.filemanager.core.OrderOption
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

        binding.sortMenuBtn.setOnClickListener {
            val popup = PopupMenu(requireContext(), it,)
            popup.menuInflater.inflate(R.menu.sorting_menu, popup.menu)
            popup.show()
            popup.setOnMenuItemClickListener { menuItem ->
                viewModel.sortBy(
                when (menuItem.itemId) {
                    R.id.sort_name_asc -> OrderOption.ByNameASC
                    R.id.sort_name_desc -> OrderOption.ByNameDESC
                    R.id.sort_extension_asc-> OrderOption.ByExtensionASC
                    R.id.sort_extension_desc-> OrderOption.ByExtensionDESC
                    R.id.sort_size_asc-> OrderOption.BySizeASC
                    R.id.sort_size_desc-> OrderOption.BySizeDESC
                    R.id.sort_time_asc-> OrderOption.ByTimeASC
                    R.id.sort_time_desc-> OrderOption.ByTimeDESC
                    else -> OrderOption.ByNameASC
                    }
                )
                return@setOnMenuItemClickListener true
            }
        }

        lifecycleScope.launch {
            viewModel.collectPermissionStateCommunication(this@FilesFragment){
                it.apply(viewModel, getString(R.string.all_files_perm_message))

            }
        }


        lifecycleScope.launch {
            viewModel.collectFiles(this@FilesFragment){
                adapter.addItems(it)
                binding.filesRcv.scrollToPosition(0)
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