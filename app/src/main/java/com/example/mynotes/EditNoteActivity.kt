package com.example.mynotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import java.util.UUID

class EditNoteActivity : AppCompatActivity() {
    private lateinit var notesViewModel: NotesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)
        // Access the NotesViewModel from the previous activity
        notesViewModel = ViewModelProvider(this)[NotesViewModel::class.java]

        // Get the TextView "editNoteId"
        val idText = findViewById<android.widget.TextView>(R.id.editNoteId)
        // Get the Floating Buttons
        val saveButton = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.editNoteSaveButton)
        val deleteButton = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.editNoteDeleteButton)
        // Get the EditTexts
        val titleEditText = findViewById<android.widget.EditText>(R.id.editNoteTitle)
        val descriptionEditText = findViewById<android.widget.EditText>(R.id.editNoteDescription)

        // Get the note ID from the intent
        val noteId = intent.extras?.getString("id")

        // Get the note from the ID
        val note = notesViewModel.getNotes().find { it.id == UUID.fromString(noteId) }
        // Convert the noteId to UUID
        val noteUUID = UUID.fromString(noteId)
        // Set the text of the TextView to the noteId
        idText.text = noteUUID.toString()
        // Set the text of the EditTexts to the note's title and description
        titleEditText.setText(note?.title)
        descriptionEditText.setText(note?.description)
        // Debug log
        println("Note ID: ${noteUUID}")
        println("Note: ${note}")
        println("Note title: ${note?.title}")
        println("Note description: ${note?.description}")

        // Set click listener for the save button
        saveButton.setOnClickListener {
            // Update the note
            notesViewModel.updateById(
                noteUUID,
                Notes(
                    title = titleEditText.text.toString(),
                    description = descriptionEditText.text.toString()
                )
            )
            // Finish the activity
            finish()
        }

        // Set click listener for the delete button
        deleteButton.setOnClickListener {
            // Delete the note
            notesViewModel.deleteById(noteUUID)
            // Finish the activity
            finish()
        }
    }
}