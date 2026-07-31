package com.nextgendevs.quicknotes.data.mapper

import com.nextgendevs.quicknotes.data.local.TodoEntity
import com.nextgendevs.quicknotes.domain.model.Todo

fun TodoEntity.toDomain(): Todo {
    return Todo(
        id = id,
        title = title,
        completed = completed,
        reminderTime = reminderTime,
        createdAt = createdAt
    )
}

fun Todo.toEntity(): TodoEntity {
    return TodoEntity(
        id = id,
        title = title,
        completed = completed,
        reminderTime = reminderTime,
        createdAt = createdAt
    )
}