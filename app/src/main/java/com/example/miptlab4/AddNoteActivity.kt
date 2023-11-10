package com.example.miptlab4

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.miptlab4.databinding.ActivityAddNoteBinding
import java.io.FileNotFoundException
import java.io.FileOutputStream

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnCreate.setOnClickListener {
            val fileName = binding.etTitle.text.toString()
            val note = binding.etNote.text.toString()

            if(fileName.isNotEmpty() && fileName.trim() != "") {

                val fileOutputStream: FileOutputStream

                try {
                    fileOutputStream = openFileOutput(fileName, MODE_PRIVATE) // ISPEJIMAS: EGZISTUOJANTIS FAILAS GALI BUTI PERRASYTAS
                    fileOutputStream.write(note.toByteArray())
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                Log.d("CheckThis", "File is saved!")

                Toast.makeText(this, "Your note was created!", Toast.LENGTH_SHORT).show()

                finish()

            } else {
                Log.d("CheckThis", "File name is empty!")
                Toast.makeText(this, "Your note title is empty!", Toast.LENGTH_SHORT).show()
            }
        }

    }
}