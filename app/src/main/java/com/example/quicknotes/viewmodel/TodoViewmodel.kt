package com.example.quicknotes.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quicknotes.data.AppDatabase
import com.example.quicknotes.data.repository.TodoRepositoryImpl
import com.example.quicknotes.domain.model.Todo
import com.example.quicknotes.domain.repository.TodoRepository
import com.example.quicknotes.notification.ReminderScheduler
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val repo: TodoRepository,
    @ApplicationContext private val context: Context
) : ViewModel()
{
    val todos = repo.getAllTodos().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun insert(todo: Todo) {
        viewModelScope.launch {
            val insertedId = repo.insertTodo(todo)
            todo.reminderTime?.let { reminderTime ->
                ReminderScheduler.scheduleReminder(
                    context = context,
                    taskId = insertedId.toInt(),
                    title = todo.title,
                    timeInMillis = reminderTime
                )
            }
        }
    }

    fun delete(todo: Todo) {
        viewModelScope.launch {
            repo.deleteTodo(todo)
        }
    }

    fun toggleTodo(todo: Todo) {
        viewModelScope.launch {
            repo.updateTodo(
                todo.copy(completed = !todo.completed)
            )
        }
    }

    fun isNotEmpty(): Boolean {
        return todos.value.isNotEmpty()
    }

    fun clearAll() {
        viewModelScope.launch {
            repo.clearAllTodos()
        }
    }
}