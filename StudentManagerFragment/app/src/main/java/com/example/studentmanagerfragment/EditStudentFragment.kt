package com.example.studentmanagerfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

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
        if (position != -1) {
            val student = StudentData.studentList[position]
            nameInput.setText(student.name)
            studentIdInput.setText(student.studentId)
        }

        saveButton.setOnClickListener {
            if (position != -1) {
                StudentData.studentList[position].apply {
                    name = nameInput.text.toString()
                    studentId = studentIdInput.text.toString()
                }
                findNavController().navigateUp()
            }
        }

        return view
    }
}
