package com.ptg.task4

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ptg.task4.Storage.NoteDb
import com.ptg.task4.databinding.ActivityAddBinding
import com.ptg.task4.databinding.ActivityMainBinding


class AddActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide()
        setContentView(R.layout.activity_add)
        binding= ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var ReceivedTitleText=binding.titleText
        var ReceivedDescText=binding.DescText

        ////////////////////////////////////////////////////////////////////
        val settingButton: Button = findViewById(R.id.settingbtn)
        // Set the tint color programmatically
        val color = Color.parseColor("#0BBAA5")
        val colorStateList = ColorStateList.valueOf(color)
        settingButton.backgroundTintList = colorStateList
        ////////////////////////////////////////////////////////////////////

        binding.backbtn.setOnClickListener {
            Log.d(TAG,"back btn clicked")
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)

        }

        binding.settingbtn.setOnClickListener {
            Log.d(TAG,"setting btn clicked")
            val intent= Intent(this,SettingsActivity::class.java)
            startActivity(intent)

        }
        binding.savebtn.setOnClickListener {
            Log.d(TAG,"save btn clicked")

            val title=ReceivedTitleText.text.toString()
            val desc=ReceivedDescText.text.toString()

            if(title=="")
            {
                Toast.makeText(this, "Please fill details.", Toast.LENGTH_SHORT).show()
            }
            else{
                val database= NoteDb(context = this) //created variable of NoteDb
                database.addNote(title,desc)
                binding.titleText.setText("")
                binding.DescText.setText("")

                Toast.makeText(this, "Note Saved...", Toast.LENGTH_SHORT).show()
            }


        }


    }

    companion object
    {
        const val TAG="AddActivity"
    }
}