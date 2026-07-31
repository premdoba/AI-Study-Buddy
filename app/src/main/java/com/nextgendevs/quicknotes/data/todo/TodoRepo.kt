package com.nextgendevs.quicknotes.repository

import com.nextgendevs.quicknotes.data.local.TodoDao
import com.nextgendevs.quicknotes.data.local.TodoEntity

class TodoRepository(
    private val dao: TodoDao
) {

    val allTodos = dao.getAllTodos()

    suspend fun insert(todo: TodoEntity): Long {
        return dao.insertTodo(todo)
    }

    suspend fun update(todo: TodoEntity) {
        dao.updateTodo(todo)
    }

    suspend fun delete(todo: TodoEntity) {
        dao.deleteTodo(todo)
    }

    suspend fun clearAll() {
        dao.clearAllTodos()
    }
}