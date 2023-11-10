package com.example.miptlab4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.example.miptlab4.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var arrayAdapter: ArrayAdapter<String>
    private lateinit var records: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("CheckThis", "Starting the main activity!")
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        records = getFileNames()

        arrayAdapter = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, records
        )

        binding.listView.adapter = arrayAdapter

        binding.listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedItem = records[position]

            showItemDialog(selectedItem)
        }

        binding.button1.setOnClickListener {
            Intent(this, AddNoteActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.button2.setOnClickListener {
            Intent(this, DeleteNoteActivity::class.java).also {
                startActivity(it)
            }
        }

    }

    override fun onResume() {
        Log.d("CheckThis", "Starting the main activity again!")
        super.onResume()

        records = getFileNames()

        arrayAdapter = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, records
        )

        binding.listView.adapter = arrayAdapter

    }


    private fun getFileNames(): List<String> {
        Log.d("CheckThis", "Searching for file names!")
        val filesDir: File = filesDir
        val fileList: Array<String> = filesDir.list() ?: arrayOf()

        return fileList.toList()
    }


    private fun showItemDialog(item: String) {
        Log.d("CheckThis", "Trying to read the file content...")
        val stringBuilder: StringBuilder = StringBuilder()

        if(item != null && item.trim() != "") {
            var fileInputStream: FileInputStream? = null
            fileInputStream = openFileInput(item)

            var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)

            var text: String? = null

            while ({text = bufferedReader.readLine(); text} () != null) {
                stringBuilder.append(text)
            }
        }

        Log.d("CheckThis", "Generating Alert Dialog window...")
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(item)
        builder.setMessage(stringBuilder.toString())

        builder.setPositiveButton("Exit") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
