package com.example.simplenumberlist

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var edtNumber: EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var btnShow: Button
    private lateinit var listView: ListView
    private lateinit var tvError: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtNumber = findViewById(R.id.edtNumber)
        radioGroup = findViewById(R.id.radioGroup)
        btnShow = findViewById(R.id.btnShow)
        listView = findViewById(R.id.listView)
        tvError = findViewById(R.id.tvError)

        btnShow.setOnClickListener {
            displayNumbers()
        }
    }

    private fun displayNumbers() {
        val inputText = edtNumber.text.toString()

        // Kiểm tra dữ liệu hợp lệ
        if (inputText.isEmpty()) {
            tvError.text = "Vui lòng nhập một số nguyên dương"
            tvError.visibility = TextView.VISIBLE
            return
        }

        val n = inputText.toIntOrNull()
        if (n == null || n < 0) {
            tvError.text = "Vui lòng nhập một số nguyên dương hợp lệ"
            tvError.visibility = TextView.VISIBLE
            return
        }

        tvError.visibility = TextView.GONE
        val numbersList = when (radioGroup.checkedRadioButtonId) {
            R.id.radioEven -> generateEvenNumbers(n)
            R.id.radioOdd -> generateOddNumbers(n)
            R.id.radioPerfectSquare -> generatePerfectSquares(n)
            else -> emptyList()
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, numbersList)
        listView.adapter = adapter
    }

    private fun generateEvenNumbers(n: Int): List<Int> {
        return (0..n).filter { it % 2 == 0 }
    }

    private fun generateOddNumbers(n: Int): List<Int> {
        return (1..n).filter { it % 2 != 0 }
    }

    private fun generatePerfectSquares(n: Int): List<Int> {
        val perfectSquares = mutableListOf<Int>()
        var i = 0
        while (i * i <= n) {
            perfectSquares.add(i * i)
            i++
        }
        return perfectSquares
    }
}
