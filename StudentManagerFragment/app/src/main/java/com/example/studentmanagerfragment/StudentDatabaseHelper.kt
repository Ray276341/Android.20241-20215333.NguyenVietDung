package com.example.studentmanagerfragment

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class StudentDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "students.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "students"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_STUDENT_ID = "student_id"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_STUDENT_ID TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addStudent(student: Student): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, student.name)
            put(COLUMN_STUDENT_ID, student.studentId)
        }
        val id = db.insert(TABLE_NAME, null, values)
        db.close()
        return id
    }

    fun updateStudent(id: Int, student: Student): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, student.name)
            put(COLUMN_STUDENT_ID, student.studentId)
        }
        val rowsUpdated = db.update(TABLE_NAME, values, "$COLUMN_ID=?", arrayOf(id.toString()))
        db.close()
        return rowsUpdated
    }

    fun deleteStudent(id: Int): Int {
        val db = writableDatabase
        val rowsDeleted = db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(id.toString()))
        db.close()
        return rowsDeleted
    }

    /*fun getAllStudents(): List<Student> {
        val studentList = mutableListOf<Student>()
        val query = "SELECT * FROM $TABLE_NAME"
        val db = readableDatabase
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val studentId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDENT_ID))
                studentList.add(Student(name, studentId).apply { this.id = id })
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return studentList
    }

    fun initializeDatabaseWithDefaultData(context: Context) {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val isDatabaseInitialized = sharedPreferences.getBoolean("is_database_initialized", false)

        if (!isDatabaseInitialized) {
            // Populate database with initial data
            val initialData = listOf(
                Student("Nguyễn Văn An", "SV001"),
                Student("Trần Thị Bảo", "SV002"),
                Student("Lê Hoàng Cường", "SV003"),
                Student("Phạm Thị Dung", "SV004"),
                Student("Đỗ Minh Đức", "SV005"),
                Student("Vũ Thị Hoa", "SV006"),
                Student("Hoàng Văn Hải", "SV007"),
                Student("Bùi Thị Hạnh", "SV008"),
                Student("Đinh Văn Hùng", "SV009"),
                Student("Nguyễn Thị Linh", "SV010"),
                Student("Phạm Văn Long", "SV011"),
                Student("Trần Thị Mai", "SV012"),
                Student("Lê Thị Ngọc", "SV013"),
                Student("Vũ Văn Nam", "SV014"),
                Student("Hoàng Thị Phương", "SV015"),
                Student("Đỗ Văn Quân", "SV016"),
                Student("Nguyễn Thị Thu", "SV017"),
                Student("Trần Văn Tài", "SV018"),
                Student("Phạm Thị Tuyết", "SV019"),
                Student("Lê Văn Vũ", "SV020")
            )
            val databaseHelper = StudentDatabaseHelper(context)
            initialData.forEach { student ->
                databaseHelper.addStudent(student)
            }

            // Mark database as initialized
            sharedPreferences.edit().putBoolean("is_database_initialized", true).apply()
        }
    }*/

}
