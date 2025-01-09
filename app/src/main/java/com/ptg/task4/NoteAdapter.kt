package com.ptg.task4

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.ptg.task4.Storage.NoteDb
import com.ptg.task4.databinding.ActivityViewBinding
import com.ptg.task4.databinding.ItemNoteBinding

class NoteAdapter(
    private val noteList:List<NoteDb.Note>,
    private val context: Context,
    private val noteDb: NoteDb) :RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteAdapter.NoteViewHolder, position: Int) {
        val note = noteList[position]
        holder.bind(note, position)
    }

    override fun getItemCount(): Int = noteList.size

    inner class NoteViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: NoteDb.Note, position: Int) {
            binding.titleTextView.text = note.title //title id from item note
            binding.descriptionTextView.text = note.description //description id from item note

             //Handle Edit button click
            binding.editButton.setOnClickListener {
                val intent = Intent(context, EditActivity::class.java).apply {
                    putExtra("noteId", note.id) // Pass the note ID
                    putExtra("noteTitle", note.title)
                    putExtra("noteDescription", note.description)
                }
                context.startActivity(intent)
            }

            // Handle Delete button click
            binding.deleteButton.setOnClickListener {
                noteDb.deleteNote(note.id)

                notifyItemRemoved(position)
            }

        }
    }
}
