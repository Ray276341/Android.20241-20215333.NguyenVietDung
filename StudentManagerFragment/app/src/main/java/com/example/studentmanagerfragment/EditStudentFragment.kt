package com.example.studentmanagerfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch

class EditStudentFragment : Fragment() {
    private lateinit var nameInput: EditText
    private lateinit var studentIdInput: EditText
    private lateinit var saveButton: Button
    private var position: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_student, container, false)

        nameInput = view.findViewById(R.id.name_input)
        studentIdInput = view.findViewById(R.id.student_id_input)
        saveButton = view.findViewById(R.id.save_button)

        position = arguments?.getInt("position") ?: -1

        // Check if position is valid and load student data asynchronously
        if (position != -1) {
            // Fetch the student list asynchronously
            lifecycleScope.launch {
                val students = StudentData.getAllStudents()
                val student = students.getOrNull(position)
                student?.let {
                    nameInput.setText(it.name)
                    studentIdInput.setText(it.studentId)
                }
            }
        }

        saveButton.setOnClickListener {
            // Save the updated student data asynchronously
            if (position != -1) {
                lifecycleScope.launch {
                    val students = StudentData.getAllStudents()
                    val student = students.getOrNull(position)
                    student?.let {
                        // Update the student's information
                        it.name = nameInput.text.toString()
                        it.studentId = studentIdInput.text.toString()

                        // Update student in the database
                        StudentData.updateStudent(it.id, it)
                        // Navigate back
                        findNavController().navigateUp()
                    }
                }
            }
        }

        return view
    }
}
