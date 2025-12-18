package ute.android.lab_10

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "ToDoList.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_USERS = "users"
        const val COLUMN_USER_ID = "id"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_PASSWORD = "password"

        const val TABLE_TASKS = "tasks"
        const val COLUMN_TASK_ID = "id"
        const val COLUMN_TASK_USER_ID = "user_id"
        const val COLUMN_TASK_TITLE = "title"
        const val COLUMN_TASK_CONTENT = "content"
        const val COLUMN_TASK_IS_DONE = "is_done"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createUsersTable = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USERNAME TEXT,
                $COLUMN_PASSWORD TEXT
            )
        """.trimIndent()

        val createTasksTable = """
            CREATE TABLE $TABLE_TASKS (
                $COLUMN_TASK_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TASK_USER_ID INTEGER,
                $COLUMN_TASK_TITLE TEXT,
                $COLUMN_TASK_CONTENT TEXT,
                $COLUMN_TASK_IS_DONE INTEGER
            )
        """.trimIndent()

        db?.execSQL(createUsersTable)
        db?.execSQL(createTasksTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_TASKS")
        onCreate(db)
    }

    fun addUser(user: User): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, user.username)
            put(COLUMN_PASSWORD, user.password)
        }
        val success = db.insert(TABLE_USERS, null, values)
        db.close()
        return success
    }

    fun checkUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val columns = arrayOf(COLUMN_USER_ID)
        val selection = "$COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?"
        val selectionArgs = arrayOf(username, password)

        val cursor = db.query(
            TABLE_USERS, columns, selection, selectionArgs,
            null, null, null
        )

        val count = cursor.count
        cursor.close()
        db.close()

        return count > 0
    }

    fun addTask(task: Task): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TASK_USER_ID, task.userId)
            put(COLUMN_TASK_TITLE, task.title)
            put(COLUMN_TASK_CONTENT, task.content)
            put(COLUMN_TASK_IS_DONE, if (task.isDone) 1 else 0)
        }
        return db.insert(TABLE_TASKS, null, values)
    }

    fun getTasks(userId: Int): List<Task> {
        val taskList = mutableListOf<Task>()
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_TASKS WHERE $COLUMN_TASK_USER_ID = ?",
            arrayOf(userId.toString())
        )

        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val task = Task(
                        it.getInt(it.getColumnIndexOrThrow(COLUMN_TASK_ID)),
                        it.getInt(it.getColumnIndexOrThrow(COLUMN_TASK_USER_ID)),
                        it.getString(it.getColumnIndexOrThrow(COLUMN_TASK_TITLE)),
                        it.getString(it.getColumnIndexOrThrow(COLUMN_TASK_CONTENT)),
                        it.getInt(it.getColumnIndexOrThrow(COLUMN_TASK_IS_DONE)) == 1
                    )
                    taskList.add(task)
                } while (it.moveToNext())
            }
        }
        return taskList
    }

    fun updateTask(task: Task): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TASK_TITLE, task.title)
            put(COLUMN_TASK_CONTENT, task.content)
            put(COLUMN_TASK_IS_DONE, if (task.isDone) 1 else 0)
        }
        return db.update(TABLE_TASKS, values, "$COLUMN_TASK_ID = ?", arrayOf(task.id.toString()))
    }

    fun deleteTask(taskId: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_TASKS, "$COLUMN_TASK_ID = ?", arrayOf(taskId.toString()))
    }
}

