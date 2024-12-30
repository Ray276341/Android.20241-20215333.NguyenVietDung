package com.example.studentmanagerfragment

import androidx.room.*

@Dao
interface StudentDao {
    @Insert
    suspend fun addStudent(student: Student)

    @Update
    suspend fun updateStudent(student: Student)

    @Delete
    suspend fun deleteStudent(student: Student)

    @Query("SELECT * FROM students")
    suspend fun getAllStudents(): List<Student>
}

