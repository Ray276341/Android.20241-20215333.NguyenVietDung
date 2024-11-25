package com.example.studentmanager1911

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var studentAdapter: StudentAdapter
    private val students = mutableListOf(
        StudentModel("Nguyễn Văn An", "SV001"),
        StudentModel("Trần Thị Bảo", "SV002"),
        StudentModel("Lê Hoàng Cường", "SV003"),
        StudentModel("Phạm Thị Dung", "SV004"),
        StudentModel("Đỗ Minh Đức", "SV005"),
        StudentModel("Vũ Thị Hoa", "SV006"),
        StudentModel("Hoàng Văn Hải", "SV007"),
        StudentModel("Bùi Thị Hạnh", "SV008"),
        StudentModel("Đinh Văn Hùng", "SV009"),
        StudentModel("Nguyễn Thị Linh", "SV010"),
        StudentModel("Phạm Văn Long", "SV011"),
        StudentModel("Trần Thị Mai", "SV012"),
        StudentModel("Lê Thị Ngọc", "SV013"),
        StudentModel("Vũ Văn Nam", "SV014"),
        StudentModel("Hoàng Thị Phương", "SV015"),
        StudentModel("Đỗ Văn Quân", "SV016"),
        StudentModel("Nguyễn Thị Thu", "SV017"),
        StudentModel("Trần Văn Tài", "SV018"),
        StudentModel("Phạm Thị Tuyết", "SV019"),
        StudentModel("Lê Văn Vũ", "SV020")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        studentAdapter = StudentAdapter(
            students,
            onEditClick = { student -> showEditDialog(student) },
            onDeleteClick = { student, position -> showDeleteConfirmation(student, position) }
        )

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_students)
        recyclerView.adapter = studentAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        registerForContextMenu(recyclerView)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = item.groupId
        val student = students[position]

        return when (item.itemId) {
            1 -> { // Edit
                showEditDialog(student)
                true
            }
            2 -> { // Remove
                showDeleteConfirmation(student, position)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add_new -> {
                showAddDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showAddDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_student, null)
        val studentNameInput = dialogView.findViewById<EditText>(R.id.edit_student_name)
        val studentIdInput = dialogView.findViewById<EditText>(R.id.edit_student_id)

        AlertDialog.Builder(this)
            .setTitle("Add New Student")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val newStudent = StudentModel(
                    studentName = studentNameInput.text.toString(),
                    studentId = studentIdInput.text.toString()
                )
                studentAdapter.addStudent(newStudent)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showEditDialog(student: StudentModel) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_student, null)
        val studentNameInput = dialogView.findViewById<EditText>(R.id.edit_student_name)
        val studentIdInput = dialogView.findViewById<EditText>(R.id.edit_student_id)

        studentNameInput.setText(student.studentName)
        studentIdInput.setText(student.studentId)

        AlertDialog.Builder(this)
            .setTitle("Edit Student")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val updatedStudent = StudentModel(
                    studentName = studentNameInput.text.toString(),
                    studentId = studentIdInput.text.toString()
                )
                val position = students.indexOf(student)
                studentAdapter.updateStudent(updatedStudent, position)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showDeleteConfirmation(student: StudentModel, position: Int) {
        AlertDialog.Builder(this)
            .setMessage("Are you sure you want to delete this student?")
            .setPositiveButton("Delete") { _, _ ->
                studentAdapter.removeStudent(position)
                Snackbar.make(findViewById(R.id.main), "Student deleted", Snackbar.LENGTH_LONG)
                    .show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}