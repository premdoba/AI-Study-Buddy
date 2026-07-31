package com.nextgendevs.quicknotes.data.repository

import com.nextgendevs.quicknotes.data.mapper.toDomain
import com.nextgendevs.quicknotes.data.mapper.toEntity
import com.nextgendevs.quicknotes.data.study.StudyDao
import com.nextgendevs.quicknotes.domain.model.StudyHistory
import com.nextgendevs.quicknotes.domain.repository.StudyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StudyRepositoryImpl(
    private val dao: StudyDao
) : StudyRepository {

    override fun getAllHistory(): Flow<List<StudyHistory>> {
        return dao.getAllHistory().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun insertHistory(item: StudyHistory) {
        dao.insertHistory(item.toEntity())
    }

    override suspend fun getById(id: Int): StudyHistory? {
        return dao.getById(id)?.toDomain()
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

    override suspend fun deleteById(id: Int) {
        dao.deleteById(id)
    }
}