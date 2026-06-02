package com.example.quicknotes.data.repository

import com.example.quicknotes.data.remote.UsageRemoteDataSource
import com.example.quicknotes.domain.repository.UsageRepository
import javax.inject.Inject

class UsageRepositoryImpl(
    private val remote: UsageRemoteDataSource
) : UsageRepository {

    override suspend fun isUsageAllowed() =
        remote.isUsageAllowed()

    override suspend fun increment() = remote.incrementUsage()

    override suspend fun getRemainingUsage() = remote.getRemainingUsage()
}