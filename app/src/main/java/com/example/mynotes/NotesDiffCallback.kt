package com.example.mynotes

import androidx.recyclerview.widget.DiffUtil

class NotesDiffCallback(
    private val oldList: List<Notes>,
    private val newList: List<Notes>
) : DiffUtil.Callback() {
    // Compare the IDs of the notes
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    // Compare the IDs of the notes
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id
    // Compare the contents of the notes
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}
