package com.example.mynotes

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import java.util.UUID

class MainActivity : AppCompatActivity() {
    internal lateinit var notesViewModel: NotesViewModel
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var notesSearchUtil: NotesUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get the RecyclerView
        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.notesRecyclerView)
        // Get the floating action button
        val addNoteButton = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.addNoteButton)
        // Get the search view


        // Set the Toolbar as the action bar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Initialize ViewModel
        notesViewModel = ViewModelProvider(this)[NotesViewModel::class.java]
        // Initialize NotesUtils
        notesSearchUtil = NotesUtils(notesViewModel.getNotes())
        // Initialize RecyclerView and Adapter
        notesAdapter = NotesAdapter(notesViewModel.getNotes(), notesViewModel, onEditCallback = this::onEditCallback)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = notesAdapter

        // Add Note button click listener
        addNoteButton.setOnClickListener() {
            notesViewModel.addNote(
                Notes(
                    title = "New Note " + ((notesViewModel.getNotes().size + 1).toString()),
                    description = "Description"
                )
            )
            // Notify the adapter that the data set has changed
            notesAdapter.notifyItemInserted(notesViewModel.getNotes().size - 1)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the options menu from XML
        menuInflater.inflate(R.menu.options_menu, menu)
        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView?

        // Set an OnQueryTextListener to the SearchView
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // Called when the user submits the query
            override fun onQueryTextSubmit(query: String?): Boolean {
                // We don't need to do anything here since we are searching every time the query changes
                return false
            }
            // Called when the query text is changed by the user
            override fun onQueryTextChange(newText: String?): Boolean {
                notesAdapter.updateList(notesSearchUtil.search(newText.orEmpty()))
                return true
            }
        })
        return true
    }

    fun onEditCallback(id: UUID) {
        // Open the EditNoteFragment
        val fragmentManager: FragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val fragment = EditNoteFragment(notesViewModel, id)
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}