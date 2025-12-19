package ute.android.lab_11

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ToDoRepository

    val inputUsername = MutableLiveData<String>()
    val inputPassword = MutableLiveData<String>()
    val inputTaskTitle = MutableLiveData<String>()

    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage

    val userTasks: LiveData<List<Task>>

    init {
        val dao = ToDoDatabase.getDatabase(application).toDoDao()
        repository = ToDoRepository(dao)

        userTasks = _currentUser.switchMap { user ->
            if (user != null) {
                repository.getTasksByUser(user.username).asLiveData()
            } else {
                MutableLiveData(emptyList())
            }
        }
    }

    fun onRegisterClick() {
        val username = inputUsername.value ?: ""
        val password = inputPassword.value ?: ""

        if (username.isBlank() || password.isBlank()) {
            _toastMessage.value = "Nhập thiếu thông tin rồi"
            return
        }

        viewModelScope.launch {
            val existingUser = repository.checkUserExists(username)
            if (existingUser != null) {
                _toastMessage.value = "Tài khoản đã tồn tại"
            } else {
                val newUser = User(username = username, password = password)
                repository.registerUser(newUser)
                _toastMessage.value = "Đăng ký thành công"
            }
        }
    }

    fun onLoginClick() {
        val username = inputUsername.value ?: ""
        val password = inputPassword.value ?: ""

        viewModelScope.launch {
            val user = repository.login(username, password)
            if (user != null) {
                _currentUser.value = user
                _toastMessage.value = "Đăng nhập thành công"
            } else {
                _toastMessage.value = "Sai tài khoản hoặc mật khẩu"
            }
        }
    }

    fun onAddTaskClick() {
        val title = inputTaskTitle.value ?: ""
        val user = _currentUser.value

        if (title.isNotBlank() && user != null) {
            viewModelScope.launch {
                val newTask = Task(title = title, ownerUsername = user.username)
                repository.insertTask(newTask)
                inputTaskTitle.value = ""
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }

    fun updateTaskStatus(task: Task, isCompleted: Boolean) {
        viewModelScope.launch {
            repository.updateTask(task.copy(isCompleted = isCompleted))
        }
    }

    fun onLogout() {
        _currentUser.value = null
        inputUsername.value = ""
        inputPassword.value = ""
        inputTaskTitle.value = ""
    }

    fun clearToast() {
        _toastMessage.value = null
    }
}