package com.example.searchlist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    private lateinit var edtSearch: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentAdapter
    private lateinit var allStudents: List<Student>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtSearch = findViewById(R.id.edtSearch)
        recyclerView = findViewById(R.id.recyclerView)

        allStudents = listOf(
            Student("Nguyễn Văn A", "20120001"),
            Student("Trần Thị B", "20120002"),
            Student("Lê Văn C", "20120003"),
            Student("Phạm Văn D", "20120004"),
            Student("Hoàng Thị E", "20120005"),
            Student("Nguyễn Văn F", "20120006"),
            Student("Trần Thị G", "20120007"),
            Student("Lê Văn H", "20120008"),
            Student("Phạm Văn I", "20120009"),
            Student("Hoàng Thị J", "20120010"),
            Student("Nguyễn Văn K", "20120011"),
            Student("Trần Thị L", "20120012"),
            Student("Lê Văn M", "20120013"),
            Student("Phạm Văn N", "20120014"),
            Student("Hoàng Thị O", "20120015"),
            Student("Nguyễn Văn P", "20120016"),
            Student("Trần Thị Q", "20120017"),
            Student("Lê Văn R", "20120018"),
            Student("Phạm Văn S", "20120019"),
            Student("Hoàng Thị T", "20120020")
        )

        adapter = StudentAdapter(allStudents)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterList(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterList(keyword: String) {
        if (keyword.length < 3) {
            adapter.updateData(allStudents)
        } else {
            val filteredList = allStudents.filter { student ->
                student.name.contains(keyword, ignoreCase = true) ||
                        student.mssv.contains(keyword, ignoreCase = true)
            }
            adapter.updateData(filteredList)
        }
    }
}
