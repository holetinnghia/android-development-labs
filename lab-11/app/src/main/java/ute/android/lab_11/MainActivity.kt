package ute.android.lab_11

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ute.android.lab_11.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel: ToDoViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = TaskAdapter(viewModel)
        binding.recyclerViewTasks.adapter = adapter

        viewModel.userTasks.observe(this) { tasks ->
            adapter.submitList(tasks)
        }

        viewModel.toastMessage.observe(this) { message ->
            message?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                viewModel.clearToast()
            }
        }
    }
}