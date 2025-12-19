package ute.android.lab_11

import kotlinx.coroutines.flow.Flow

class ToDoRepository(private val toDoDao: ToDoDao) {

    suspend fun registerUser(user: User) {
        toDoDao.registerUser(user)
    }

    suspend fun login(username: String, password: String): User? {
        return toDoDao.login(username, password)
    }

    suspend fun checkUserExists(username: String): User? {
        return toDoDao.getUserByUsername(username)
    }

    fun getTasksByUser(username: String): Flow<List<Task>> {
        return toDoDao.getTasksByUser(username)
    }

    suspend fun insertTask(task: Task) {
        toDoDao.insertTask(task)
    }

    suspend fun updateTask(task: Task) {
        toDoDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        toDoDao.deleteTask(task)
    }
}