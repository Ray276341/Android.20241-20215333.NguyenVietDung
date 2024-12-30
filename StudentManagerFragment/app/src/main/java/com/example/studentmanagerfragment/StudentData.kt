package com.example.studentmanagerfragment

import android.content.Context

object StudentData {
    private lateinit var studentDao: StudentDao

    fun initialize(context: Context) {
        val database = StudentDatabase.getDatabase(context)
        studentDao = database.studentDao()
    }

    suspend fun getAllStudents(): List<Student> = studentDao.getAllStudents()

    suspend fun addStudent(student: Student) {
        studentDao.addStudent(student)
    }

    suspend fun updateStudent(student1: Int, student: Student) {
        studentDao.updateStudent(student)
    }

    suspend fun deleteStudent(student: Student) {
        studentDao.deleteStudent(student)
    }
}
