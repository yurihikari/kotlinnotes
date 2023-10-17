package com.example.mynotes

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.databinding.CardViewBinding
import java.util.UUID

class NotesAdapter(
    private var notesList: List<Notes>,
    private val notesViewModel: NotesViewModel,
    private val onEditCallback: (id: UUID) -> Unit
) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: CardViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val cardLayout = binding.constraintLayout
        val titleTextView = binding.cardTitle
        val descriptionTextView = binding.cardDescription
        val trashIcon = binding.trashIcon
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardViewBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notesList[position]
        holder.titleTextView.text = note.title
        holder.descriptionTextView.text = note.description

        // Set click listener for the card layout
        holder.cardLayout.setOnClickListener {
            // Navigate to the EditNoteActivity
            onEditCallback(note.id)
        }
        // Set click listener for the trash icon
        holder.trashIcon.setOnClickListener {
            // Toast "Hold to delete"
            Toast.makeText(it.context, "Hold to delete", Toast.LENGTH_SHORT).show()
        }

        // Set long press listener for the trash icon
        holder.trashIcon.setOnLongClickListener {
            // Delete the note from the list
            notesViewModel.deleteById(note.id)
            notifyDataSetChanged()
            true
        }

    }

    fun updateList(newNotesList: List<Notes>) {
        // Calculate the difference between the old list and the new list
        val diffCallback = NotesDiffCallback(notesList, newNotesList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        notesList = newNotesList
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }
}
