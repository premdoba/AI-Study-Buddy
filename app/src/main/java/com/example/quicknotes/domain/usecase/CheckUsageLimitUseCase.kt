package com.example.quicknotes.domain.usecase

import com.example.quicknotes.domain.repository.UsageRepository

class CheckUsageLimitUseCase(
    private val repository: UsageRepository
) {

    suspend operator fun invoke(): Boolean {
        return repository.isUsageAllowed()
    }

}