package com.example.studentmanagerfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class AddStudentFragment : Fragment() {
    private lateinit var nameInput: EditText
    private lateinit var studentIdInput: EditText
    private lateinit var addButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_student, container, false)

        nameInput = view.findViewById(R.id.name_input)
        studentIdInput = view.findViewById(R.id.student_id_input)
        addButton = view.findViewById(R.id.add_button)

        addButton.setOnClickListener {
            val name = nameInput.text.toString()
            val studentId = studentIdInput.text.toString()
            if (name.isNotBlank() && studentId.isNotBlank()) {
                StudentData.addStudent(Student(name, studentId))
                findNavController().navigateUp()
            }
        }


        return view
    }
}
