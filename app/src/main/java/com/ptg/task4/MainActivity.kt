package com.ptg.task4

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ptg.task4.Storage.NoteDb
import com.ptg.task4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.addNotebtn.setOnClickListener {
            Log.d("mainActivity","add note btn clicked")
            val intent=Intent(this,AddActivity::class.java)
            startActivity(intent)

        }

        binding.viewnotesbtn.setOnClickListener {
            Log.d("mainActivity","view note btn clicked")
            val intent=Intent(this,ViewActivity::class.java)
            startActivity(intent)
        }


    }

    companion object
    {
        const val TAG="MainActivity"
    }
}