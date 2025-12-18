package ute.android.lab_10

data class Task(
    val id: Int = -1,
    val userId: Int,
    val title: String,
    val content: String,
    val isDone: Boolean = false
)