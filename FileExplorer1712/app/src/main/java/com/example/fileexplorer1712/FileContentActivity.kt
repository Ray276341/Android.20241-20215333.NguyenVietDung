package com.example.fileexplorer1712

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class FileContentActivity : AppCompatActivity() {

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_content)

        textView = findViewById(R.id.textView)
        val filePath = intent.getStringExtra("filePath")

        if (filePath != null) {
            val file = File(filePath)
            if (file.exists() && file.isFile) {
                textView.text = file.readText()
            } else {
                textView.text = "Không thể mở file này."
            }
        } else {
            textView.text = "File không tồn tại."
        }
    }
}
