package com.nextgendevs.quicknotes.domain.repository

import com.nextgendevs.quicknotes.domain.model.Mcq
import com.nextgendevs.quicknotes.domain.model.StudyNotes

interface AiRepository {
    suspend fun generateStudyMaterial(input: String, educationLevel: String): StudyNotes?
    suspend fun askDoubt(question: String, educationLevel: String, contextText: String, historyText: String): String
    suspend fun generateMcqs(input: String, educationLevel: String, difficulty: String): Pair<List<Mcq>, String>?
}