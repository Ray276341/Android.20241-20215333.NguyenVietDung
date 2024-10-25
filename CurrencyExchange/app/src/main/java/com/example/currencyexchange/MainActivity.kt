package com.example.currencyexchange

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.currencyexchange.R

class MainActivity : AppCompatActivity() {
    private lateinit var sourceAmount: EditText
    private lateinit var convertedAmount: EditText
    private lateinit var sourceCurrencySpinner: Spinner
    private lateinit var targetCurrencySpinner: Spinner

    // Tỷ giá hối đoái giả định
    private val exchangeRates = mapOf(
        "VND" to 1.0,         // 1 VND = 1 VND
        "USD" to 1 / 25369.96, // 1 USD = 24000 VND => 1 VND = 1/24000 USD
        "JPY" to 1 / 166.74,   // 1 JPY = 200 VND => 1 VND = 1/200 JPY
        "CNY" to 1 / 3562.9,  // 1 CNY = 3500 VND => 1 VND = 1/3500 CNY
        "GBP" to 1 / 32903.40  // 1 GBP = 29000 VND => 1 VND = 1/29000 GBP
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sourceAmount = findViewById(R.id.sourceAmount)
        convertedAmount = findViewById(R.id.convertedAmount)
        sourceCurrencySpinner = findViewById(R.id.sourceCurrencySpinner)
        targetCurrencySpinner = findViewById(R.id.targetCurrencySpinner)

        // Cấu hình Spinner với danh sách tiền tệ
        val currencyList = exchangeRates.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencyList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sourceCurrencySpinner.adapter = adapter
        targetCurrencySpinner.adapter = adapter

        // Xử lý cập nhật khi người dùng nhập liệu hoặc thay đổi đơn vị tiền tệ
        sourceAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { convertCurrency() }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        sourceCurrencySpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                convertCurrency() // Gọi hàm chuyển đổi
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Xử lý khi không có mục nào được chọn nếu cần
            }
        })

        targetCurrencySpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                convertCurrency() // Gọi hàm chuyển đổi
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Xử lý khi không có mục nào được chọn nếu cần
            }
        })
    }

    private fun convertCurrency() {
        val sourceCurrency = sourceCurrencySpinner.selectedItem.toString()
        val targetCurrency = targetCurrencySpinner.selectedItem.toString()
        val amountText = sourceAmount.text.toString()

        // Kiểm tra giá trị nguồn có hợp lệ không
        if (amountText.isNotEmpty()) {
            val sourceAmountValue = amountText.toDouble()
            val sourceRate = exchangeRates[sourceCurrency] ?: 1.0
            val targetRate = exchangeRates[targetCurrency] ?: 1.0

            // Tính toán số tiền quy đổi
            val convertedValue = (sourceAmountValue * targetRate) / sourceRate
            convertedAmount.setText("%.2f".format(convertedValue))
        } else {
            convertedAmount.setText("")
        }
    }

}
