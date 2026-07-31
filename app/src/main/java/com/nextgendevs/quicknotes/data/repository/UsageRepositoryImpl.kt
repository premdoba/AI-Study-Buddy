package com.nextgendevs.quicknotes.data.repository

import com.nextgendevs.quicknotes.data.remote.UsageRemoteDataSource
import com.nextgendevs.quicknotes.domain.repository.UsageRepository

class UsageRepositoryImpl(
    private val remote: UsageRemoteDataSource
) : UsageRepository {

    override suspend fun isUsageAllowed() =
        remote.isUsageAllowed()

    override suspend fun increment() = remote.incrementUsage()

    override suspend fun getRemainingUsage() = remote.getRemainingUsage()
}