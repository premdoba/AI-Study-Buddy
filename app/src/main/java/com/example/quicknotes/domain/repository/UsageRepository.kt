package com.example.quicknotes.domain.repository

interface UsageRepository {
    suspend fun isUsageAllowed(): Boolean
    suspend fun increment()
    suspend fun getRemainingUsage(): Long
}