package com.nextgendevs.quicknotes.data.repository

import com.nextgendevs.quicknotes.data.local.TodoDao
import com.nextgendevs.quicknotes.data.mapper.toDomain
import com.nextgendevs.quicknotes.data.mapper.toEntity
import com.nextgendevs.quicknotes.domain.model.Todo
import com.nextgendevs.quicknotes.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepositoryImpl(
    private val dao: TodoDao
) : TodoRepository {

    override fun getAllTodos(): Flow<List<Todo>> {
        return dao.getAllTodos().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun insertTodo(todo: Todo): Long {
        return dao.insertTodo(todo.toEntity())
    }

    override suspend fun updateTodo(todo: Todo) {
        dao.updateTodo(todo.toEntity())
    }

    override suspend fun deleteTodo(todo: Todo) {
        dao.deleteTodo(todo.toEntity())
    }

    override suspend fun clearAllTodos() {
        dao.clearAllTodos()
    }
}