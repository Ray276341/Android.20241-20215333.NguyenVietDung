package com.example.studentmanagerfragment

data class Student(
    var name: String,
    var studentId: String,
    var id: Int = -1 // ID mặc định là -1 nếu chưa lưu vào CSDL
)
