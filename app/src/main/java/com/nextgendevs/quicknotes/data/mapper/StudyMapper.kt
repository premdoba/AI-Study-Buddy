package com.nextgendevs.quicknotes.data.mapper

import com.nextgendevs.quicknotes.data.study.StudyHistoryEntity
import com.nextgendevs.quicknotes.domain.model.StudyHistory

fun StudyHistoryEntity.toDomain(): StudyHistory {
    return StudyHistory(
        id = id,
        topic = topic,
        educationLevel = educationLevel,
        shortNotes = shortNotes,
        questions = questions,
        mcqsRaw = mcqsRaw,
        summary = summary,
        originalInput = originalInput,
        createdAt = createdAt
    )
}

fun StudyHistory.toEntity(): StudyHistoryEntity {
    return StudyHistoryEntity(
        id = id,
        topic = topic,
        educationLevel = educationLevel,
        shortNotes = shortNotes,
        questions = questions,
        mcqsRaw = mcqsRaw,
        summary = summary,
        originalInput = originalInput,
        createdAt = createdAt
    )
}