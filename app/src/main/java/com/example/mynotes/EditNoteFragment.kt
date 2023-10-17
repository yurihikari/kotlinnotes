package com.example.mynotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import java.util.UUID

class EditNoteFragment(private val notesViewModel: NotesViewModel,private val noteId: UUID) : Fragment(
) {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the Note Close Button
        val editNoteCloseButton = getView()?.findViewById<androidx.appcompat.widget.AppCompatImageView>(R.id.editNoteCloseButton)
        // Get the Note Save Button
        val editNoteSaveButton = getView()?.findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.editNoteSaveButton)
        // Get the Note Delete Button
        val editNoteDeleteButton = getView()?.findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.editNoteDeleteButton)
        // Get the editTexts
        val editNoteTitle = getView()?.findViewById<android.widget.EditText>(R.id.editNoteTitle)
        val editNoteDescription = getView()?.findViewById<android.widget.EditText>(R.id.editNoteDescription)

        // Set the text of the editTexts to the note's title and description
        editNoteTitle?.setText(notesViewModel.getNotes().find { it.id == noteId }?.title)
        editNoteDescription?.setText(notesViewModel.getNotes().find { it.id == noteId }?.description)


        // Handle the close button click
        editNoteCloseButton?.setOnClickListener {
            // Close or hide the fragment (You can use callbacks or ViewModel to communicate with your activity)
            notesViewModel.updateById(
                noteId,
                Notes(
                    title = editNoteTitle?.text.toString(),
                    description = editNoteDescription?.text.toString()
                )
            )
            // Notify the adapter that the data set has changed
            activity?.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.notesRecyclerView)?.adapter?.notifyDataSetChanged()
            activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
        }

        // Handle the save button click
        editNoteSaveButton?.setOnClickListener {
            // Save the note using the view model FROM THE ACTIVITY
            notesViewModel.updateById(
                noteId,
                Notes(
                    title = editNoteTitle?.text.toString(),
                    description = editNoteDescription?.text.toString()
                )
            )
            // Notify the adapter that the data set has changed
            activity?.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.notesRecyclerView)?.adapter?.notifyDataSetChanged()
            activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
        }

        // Handle the delete button click
        editNoteDeleteButton?.setOnClickListener {
            // Delete the note
            notesViewModel.deleteById(noteId)
            // Notify the adapter that the data set has changed
            activity?.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.notesRecyclerView)?.adapter?.notifyDataSetChanged()
            activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
        }
    }
}
