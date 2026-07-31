package com.nextgendevs.quicknotes.domain.model

data class Todo(
    val id: Int = 0,
    val title: String,
    val completed: Boolean = false,
    val reminderTime: Long? = null,
    val createdAt: Long = System.currentTimeMillis()
)