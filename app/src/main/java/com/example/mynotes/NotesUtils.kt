package com.example.mynotes

class NotesUtils(private val notesList: List<Notes>) {
    fun search(query: String): List<Notes> {
        // Filter the notes list based on the search query
        return notesList.filter { note ->
            note.title.contains(query, ignoreCase = true) ||
                    note.description.contains(query, ignoreCase = true)
        }
    }
}
