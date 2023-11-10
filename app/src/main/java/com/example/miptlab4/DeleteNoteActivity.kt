package com.example.miptlab4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.miptlab4.databinding.ActivityDeleteNoteBinding
import java.io.File

class DeleteNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeleteNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val notesTitle = getFileNames()
        val adapter = ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, notesTitle)
        binding.spinner.adapter = adapter

        binding.btnDelete.setOnClickListener {

            if(binding.spinner.selectedItem != null) {

                val selectedItem = binding.spinner.selectedItem.toString()
                val fileName = selectedItem
                val file = File(filesDir, fileName)

                val deleted = file.delete()

                Toast.makeText(this, "You deleted the note: $selectedItem", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "There is nothing to delete!", Toast.LENGTH_SHORT).show()
            }

        }

        binding.btnBack.setOnClickListener {
            finish()
        }

    }

    private fun getFileNames(): List<String> {
        Log.d("CheckThis", "Searching for file names!")
        val filesDir: File = filesDir
        val fileList: Array<String> = filesDir.list() ?: arrayOf()

        return fileList.toList()
    }
}