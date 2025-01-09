package com.ptg.task4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ptg.task4.Storage.NoteDb
import com.ptg.task4.databinding.ActivityAddBinding
import com.ptg.task4.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {

        private lateinit var titleEditText: EditText
        private lateinit var descriptionEditText: EditText
        private lateinit var saveButton: Button
        private lateinit var noteData: NoteDb
        private var noteId: Int = -1 // Initialize as -1 to check if it's set properly

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_edit)

            enableEdgeToEdge()
            supportActionBar?.hide()

            // Initialize views
            titleEditText = findViewById(R.id.titleText)
            descriptionEditText = findViewById(R.id.DescText)
            saveButton = findViewById(R.id.savebtn)

            // Retrieve data from Intent
            noteData = NoteDb(this)
            noteId = intent.getIntExtra("noteId", -1)
            val noteTitle = intent.getStringExtra("noteTitle") ?: ""
            val noteDescription = intent.getStringExtra("noteDescription") ?: ""

            // Log the received data to check if it's correct
            Log.d(
                "EditDataActivity",
                "Received ID: $noteId, Title: $noteTitle, Description: $noteDescription"
            )

            // Set the data to the EditText
            titleEditText.setText(noteTitle)
            descriptionEditText.setText(noteDescription)

            // Save button click listener
            saveButton.setOnClickListener {
                val updatedTitle = titleEditText.text.toString().trim()
                val updatedDescription = descriptionEditText.text.toString().trim()

                if (updatedTitle.isNotEmpty() && updatedDescription.isNotEmpty()) {
                    // Update the note in the database
                    if (noteId != -1) {
                        noteData.updateNote(noteId, updatedTitle, updatedDescription)
                        Toast.makeText(this, "Note updated successfully", Toast.LENGTH_SHORT).show()

                        // Pass updated data back to the calling activity
                        val resultIntent = Intent()
                        resultIntent.putExtra("updatedNoteId", noteId)
                        resultIntent.putExtra("updatedTitle", updatedTitle)
                        resultIntent.putExtra("updatedDescription", updatedDescription)
                        setResult(RESULT_OK, resultIntent)
                        finish()
                    } else {
                        Toast.makeText(this, "Error updating note", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Please fill in both title and description",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
}