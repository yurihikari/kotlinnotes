package com.example.mynotes

import androidx.lifecycle.ViewModel
import java.util.UUID

class NotesViewModel : ViewModel() {
    // List of notes (replace with your data source)
    private val notesList = mutableListOf<Notes>()

    // Function to retrieve the list of notes
    fun getNotes(): List<Notes> {
        return notesList
    }

    // Function to add a new note
    fun addNote(note: Notes) {
        notesList.add(note)
        println("Added note with ID ${note.id}")
        println("Notes list: ${notesList}")
    }

    // Function to delete a note by ID
    fun deleteById(id: UUID) {
        notesList.removeAll { it.id == id }
        println("Deleted note with ID ${id}")
        println("Notes list: ${notesList}")
    }

    // Function to update a note by ID
    fun updateById(id: UUID, note: Notes) {
        val index = notesList.indexOfFirst { it.id == id }
        notesList[index] = note
        println("Updated note with ID ${id}")
        println("Notes list: ${notesList}")
    }
}