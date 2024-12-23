package com.example.fileexplorer1712

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private var currentPath: String = Environment.getExternalStorageDirectory().path

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        displayFilesAndFolders(File(currentPath))

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedFile = File(currentPath, listView.adapter.getItem(position) as String)
            if (selectedFile.isDirectory) {
                // Nếu là thư mục, hiển thị nội dung thư mục
                currentPath = selectedFile.path
                displayFilesAndFolders(selectedFile)
            } else if (selectedFile.isFile) {
                // Nếu là file, mở nội dung file
                openFile(selectedFile)
            }
        }
    }

    private fun displayFilesAndFolders(directory: File) {
        val files = directory.listFiles()
        val fileNames = files?.map { it.name } ?: emptyList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, fileNames)
        listView.adapter = adapter
    }

    private fun openFile(file: File) {
        val intent = Intent(this, FileContentActivity::class.java)
        intent.putExtra("filePath", file.path)
        startActivity(intent)
    }
}
