package com.ptg.task4

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ptg.task4.AddActivity.Companion.TAG
import com.ptg.task4.databinding.ActivityMainBinding
import com.ptg.task4.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {


    private lateinit var binding:ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide()
        setContentView(R.layout.activity_settings)
        binding= ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val settigsPage=binding.settings

        binding.backbtn.setOnClickListener {
            Log.d(TAG,"setting btn clicked")
            val intent= Intent(this,AddActivity::class.java)
            startActivity(intent)

        }

        binding.switchButton.setOnCheckedChangeListener{_,btnValue->
            if(btnValue)
            {
                settigsPage.setBackgroundColor(resources.getColor(R.color.black))
                Toast.makeText(this, "Dark Mode ON.", Toast.LENGTH_SHORT).show()

            }
            else
            {
                settigsPage.setBackgroundColor(resources.getColor(R.color.backgroundwhite))
                Toast.makeText(this, "Dark Mode OFF.", Toast.LENGTH_SHORT).show()
            }
        }

         val seekBar=binding.seekBarProcess
        var seekBarval= binding.seekBarValue


        binding.seekBarProcess.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar, progressBar: Int, fromUser: Boolean) {
                seekBarval.text=progressBar.toInt().toString()

                Toast.makeText(this@SettingsActivity, "Progress${progressBar}", Toast.LENGTH_SHORT).show()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
//                Toast.makeText(this@SettingsActivity, "Progress", Toast.LENGTH_SHORT).show()
                Log.d("TAG","seekbar tracking start")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

//                Toast.makeText(this@SettingsActivity, "Progress", Toast.LENGTH_SHORT).show()
                Log.d("TAG","seekbar tracking stop")
            }
        })
    }
    companion object
    {
        const val TAG="SettingsActivity"
    }
}