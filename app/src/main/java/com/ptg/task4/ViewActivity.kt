package com.ptg.task4

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ptg.task4.Storage.NoteDb
import com.ptg.task4.databinding.ActivityViewBinding

class ViewActivity : AppCompatActivity() {

   private lateinit var binding: ActivityViewBinding
    private lateinit var noteData: NoteDb
    private lateinit var recyclerView: RecyclerView
    private lateinit var noteAdapter: NoteAdapter
    private var notesList = mutableListOf<NoteDb.Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide()
        setContentView(R.layout.activity_view)

        binding = ActivityViewBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        val database = NoteDb(context = this) //created variable of NoteDb
//        database.getNote()
//        val getTitle: String = database.getNote().toString()
//        Log.d("ViewActivity", getTitle)
////      binding.showingvalue.text=getTitle

        // Initialize SQLite database helper
        noteData = NoteDb(this)
        recyclerView = binding.showingvalue

        // Set the layout manager for RecyclerView (vertical list)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Fetch notes from the database
        notesList = noteData.getNote().toMutableList()

        // Set up the adapter
        noteAdapter = NoteAdapter(notesList, this, noteData)

        // Set the adapter to the RecyclerView
        recyclerView.adapter = noteAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            val updatedNoteId = data.getIntExtra("updatedNoteId", -1)
            val updatedTitle = data.getStringExtra("updatedTitle") ?: ""
            val updatedDescription = data.getStringExtra("updatedDescription") ?: ""

            // Check if valid data is passed
            if (updatedNoteId != -1 && updatedTitle.isNotEmpty() && updatedDescription.isNotEmpty()) {
                val updatedNote = notesList.find { it.id == updatedNoteId }
                if (updatedNote != null) {
                    updatedNote.title = updatedTitle
                    updatedNote.description = updatedDescription

                    // Update the note in the database
                    noteData.updateNote(updatedNoteId, updatedTitle, updatedDescription)

                    // Notify the adapter that the data has changed
                    noteAdapter.notifyDataSetChanged()
                } else {
                    Log.e("ViewActivity", "Note with ID: $updatedNoteId not found.")
                }
            } else {
                Log.e("ViewActivity", "Invalid data received in onActivityResult.")
            }
        }
    }



    // Override this if you use startActivityForResult for opening the EditMode

    @Override
    override fun onStart() {
        super.onStart()
        // Fetch notes from the database again
        notesList.clear()
        notesList.addAll(noteData.getNote())
        noteAdapter.notifyDataSetChanged()
    }
}


