package com.example.filemanager.files.presentation

import android.view.LayoutInflater
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.filemanager.core.ClickListener
import com.example.filemanager.core.LongClickListener
import com.example.filemanager.databinding.FileItemBinding

/**
 * Created by HP on 07.05.2023.
 **/
class FilesAdapter(
    private val clickListener: ClickListener<String>,
    private val longClickListener: LongClickListener<String>
): RecyclerView.Adapter<FilesViewHolder>() {
    private val items = emptyList<FileUi>().toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilesViewHolder {
        return FilesViewHolder(
            FileItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),clickListener, longClickListener
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: FilesViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun addItems(data: List<FileUi>){
        val diffUtilCallBack = FileDiffUtilCallBack(items, data)
        val result = DiffUtil.calculateDiff(diffUtilCallBack)
        items.clear()
        items.addAll(data)
        result.dispatchUpdatesTo(this)
    }
}

class FilesViewHolder(
    private val binding: FileItemBinding,
    private val clickListener: ClickListener<String>,
    private val longClickListener: LongClickListener<String>
): ViewHolder(binding.root){

    private val mapper = FileUi.ToUiMapper(binding, clickListener, longClickListener)

    fun bind(item: FileUi){
        item.map(mapper)
    }

}

class FileDiffUtilCallBack(
    private val oldList: List<FileUi>,
    private val newList: List<FileUi>
): DiffUtil.Callback(){

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].map(newList[newItemPosition])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
