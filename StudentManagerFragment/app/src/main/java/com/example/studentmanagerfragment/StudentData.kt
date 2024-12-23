package com.example.studentmanagerfragment

import android.content.Context

object StudentData {
    private lateinit var databaseHelper: StudentDatabaseHelper

    fun initialize(context: Context) {
        databaseHelper = StudentDatabaseHelper(context)
    }

    fun getAllStudents(): List<Student> = databaseHelper.getAllStudents()

    fun addStudent(student: Student) {
        databaseHelper.addStudent(student)
    }

    fun updateStudent(id: Int, student: Student) {
        databaseHelper.updateStudent(id, student)
    }

    fun deleteStudent(id: Int) {
        databaseHelper.deleteStudent(id)
    }
}
