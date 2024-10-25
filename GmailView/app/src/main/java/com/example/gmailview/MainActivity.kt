package com.example.gmailview

import EmailAdapter
import EmailItem
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emailAdapter: EmailAdapter
    private lateinit var emailList: List<EmailItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Sample data
        emailList = listOf(
            EmailItem("Sender 1", "01 thg 10", "Email Title 1", "Email content goes here.", false, false),
            EmailItem("Sender 2", "02 thg 10", "Email Title 2", "Another email content.", true, true),
            EmailItem("Sender 3", "03 thg 10", "Email Title 3", "More content to read.", false, false)
        )

        emailAdapter = EmailAdapter(emailList)
        recyclerView.adapter = emailAdapter
    }
}
