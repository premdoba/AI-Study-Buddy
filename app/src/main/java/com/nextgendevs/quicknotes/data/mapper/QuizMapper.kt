package com.nextgendevs.quicknotes.data.mapper

import com.nextgendevs.quicknotes.data.local.QuizHistoryEntity
import com.nextgendevs.quicknotes.domain.model.QuizHistory

fun QuizHistoryEntity.toDomain(): QuizHistory {
    return QuizHistory(
        id = id,
        title = title,
        date = date,
        score = score,
        totalQuestions = totalQuestions,
        quizJson = quizJson
    )
}

fun QuizHistory.toEntity(): QuizHistoryEntity {
    return QuizHistoryEntity(
        id = id,
        title = title,
        date = date,
        score = score,
        totalQuestions = totalQuestions,
        quizJson = quizJson
    )
}