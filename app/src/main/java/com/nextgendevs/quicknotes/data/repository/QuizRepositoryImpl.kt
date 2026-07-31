package com.nextgendevs.quicknotes.data.repository

import com.nextgendevs.quicknotes.data.local.QuizHistoryDao
import com.nextgendevs.quicknotes.data.mapper.toDomain
import com.nextgendevs.quicknotes.data.mapper.toEntity
import com.nextgendevs.quicknotes.domain.model.QuizHistory
import com.nextgendevs.quicknotes.domain.repository.QuizRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class QuizRepositoryImpl(
    private val dao: QuizHistoryDao
) : QuizRepository {

    override suspend fun insertQuiz(quiz: QuizHistory) {
        dao.insertQuiz(quiz.toEntity())
    }

    override fun getAllQuizHistory(): Flow<List<QuizHistory>> {
        return dao.getAllQuizHistory().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun deleteQuiz(id: Int) {
        dao.deleteQuiz(id)
    }

    override suspend fun clearQuizHistory() {
        dao.clearQuizHistory()
    }

    override fun getQuizById(id: Int): Flow<QuizHistory?> {
        return dao.getQuizById(id).map { it?.toDomain() }
    }
}