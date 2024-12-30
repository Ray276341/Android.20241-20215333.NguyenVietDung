package com.example.studentmanagerfragment

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch

class StudentListFragment : Fragment() {
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_student_list, container, false)
        listView = view.findViewById(R.id.student_list_view)

        // Update the list of students asynchronously
        updateListView()

        registerForContextMenu(listView)
        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.student_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_student) {
            findNavController().navigate(R.id.action_to_addFragment)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        requireActivity().menuInflater.inflate(R.menu.student_context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        when (item.itemId) {
            R.id.edit_student -> {
                val bundle = Bundle().apply { putInt("position", info.position) }
                findNavController().navigate(R.id.action_to_editFragment, bundle)
                return true
            }
            R.id.remove_student -> {
                // Use lifecycleScope to call getAllStudents asynchronously
                lifecycleScope.launch {
                    val students = StudentData.getAllStudents() // Call inside coroutine
                    val student = students.getOrNull(info.position) // Get the student by position

                    student?.let {
                        StudentData.deleteStudent(it) // Delete student
                        updateListView() // Re-fetch and update the list view after deletion
                    }
                }
                return true
            }
        }
        return super.onContextItemSelected(item)
    }


    private fun updateListView() {
        lifecycleScope.launch {
            // Fetch students from the database in a background thread
            val students = StudentData.getAllStudents()

            // Update UI on the main thread
            adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                students.map { "${it.name} - ${it.studentId}" }
            )
            listView.adapter = adapter
        }
    }
}
