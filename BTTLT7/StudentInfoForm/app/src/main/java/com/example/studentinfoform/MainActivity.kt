package com.example.studentinfoform

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.studentinfoform.AddressHelper
import com.example.studentinfoform.R

class StudentInfoFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Views in the form
        val edtMSSV = findViewById<EditText>(R.id.edtMSSV)
        val edtFullName = findViewById<EditText>(R.id.edtFullName)
        val radioMale = findViewById<RadioButton>(R.id.radioMale)
        val radioFemale = findViewById<RadioButton>(R.id.radioFemale)
        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        val edtPhone = findViewById<EditText>(R.id.edtPhone)

        // Date of birth selection
        val btnSelectDate = findViewById<Button>(R.id.btnSelectDate)
        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        calendarView.visibility = View.GONE

        btnSelectDate.setOnClickListener {
            // Toggle visibility of the calendar when button is clicked
            calendarView.visibility = if (calendarView.visibility == View.GONE) View.VISIBLE else View.GONE
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            // Update button text with the selected date
            btnSelectDate.text = "$dayOfMonth/${month + 1}/$year"
            // Hide the calendar after date selection
            calendarView.visibility = View.GONE
        }

        // Address fields
        val spinnerProvince = findViewById<Spinner>(R.id.spinnerProvince)
        val spinnerDistrict = findViewById<Spinner>(R.id.spinnerDistrict)
        val spinnerWard = findViewById<Spinner>(R.id.spinnerWard)

        // Hobbies checkboxes
        val cbSports = findViewById<CheckBox>(R.id.cbSports)
        val cbMovies = findViewById<CheckBox>(R.id.cbMovies)
        val cbMusic = findViewById<CheckBox>(R.id.cbMusic)

        // Agree to terms checkbox
        val cbAgree = findViewById<CheckBox>(R.id.cbAgree)

        // Submit button
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        btnSubmit.setOnClickListener {
            // Validate the form data
            val isValid = validateForm(edtMSSV, edtFullName, radioMale, radioFemale, edtEmail, edtPhone, btnSelectDate, cbAgree)

            if (isValid) {
                Toast.makeText(this, "Thông tin đã được lưu", Toast.LENGTH_SHORT).show()
            }
        }

        // Load address data
        val addressHelper = AddressHelper(resources)
        val provinces = addressHelper.getProvinces()

        val provinceAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, provinces)
        spinnerProvince.adapter = provinceAdapter

        spinnerProvince.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val province = provinces[position]
                val districts = addressHelper.getDistricts(province)
                val districtAdapter = ArrayAdapter(this@StudentInfoFormActivity, android.R.layout.simple_spinner_item, districts)
                spinnerDistrict.adapter = districtAdapter
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinnerDistrict.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val province = spinnerProvince.selectedItem as String
                val district = spinnerDistrict.selectedItem as String
                val wards = addressHelper.getWards(province, district)
                val wardAdapter = ArrayAdapter(this@StudentInfoFormActivity, android.R.layout.simple_spinner_item, wards)
                spinnerWard.adapter = wardAdapter
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun validateForm(
        edtMSSV: EditText,
        edtFullName: EditText,
        radioMale: RadioButton,
        radioFemale: RadioButton,
        edtEmail: EditText,
        edtPhone: EditText,
        btnSelectDate: Button,
        cbAgree: CheckBox
    ): Boolean {
        // Validation logic
        var isValid = true
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Thông báo")

        when {
            edtMSSV.text.isEmpty() -> {
                builder.setMessage("Vui lòng nhập MSSV.")
                isValid = false
            }
            edtFullName.text.isEmpty() -> {
                builder.setMessage("Vui lòng nhập họ tên.")
                isValid = false
            }
            !radioMale.isChecked && !radioFemale.isChecked -> {
                builder.setMessage("Vui lòng chọn giới tính.")
                isValid = false
            }
            edtEmail.text.isEmpty() -> {
                builder.setMessage("Vui lòng nhập Email.")
                isValid = false
            }
            edtPhone.text.isEmpty() -> {
                builder.setMessage("Vui lòng nhập số điện thoại.")
                isValid = false
            }
            btnSelectDate.text == "Chọn ngày sinh" -> {
                builder.setMessage("Vui lòng chọn ngày sinh.")
                isValid = false
            }
            !cbAgree.isChecked -> {
                builder.setMessage("Vui lòng đồng ý với các điều khoản.")
                isValid = false
            }
        }

        if (!isValid) builder.show()
        return isValid
    }
}
