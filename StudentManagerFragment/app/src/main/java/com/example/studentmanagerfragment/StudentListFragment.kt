package com.example.studentmanagerfragment

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class StudentListFragment : Fragment() {
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_student_list, container, false)
        listView = view.findViewById(R.id.student_list_view)

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
                val student = StudentData.getAllStudents()[info.position]
                StudentData.deleteStudent(student.id)
                updateListView()
                return true
            }

        }
        return super.onContextItemSelected(item)
    }

    private fun updateListView() {
        val students = StudentData.getAllStudents()
        adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            students.map { "${it.name} - ${it.studentId}" }
        )
        listView.adapter = adapter
    }

}