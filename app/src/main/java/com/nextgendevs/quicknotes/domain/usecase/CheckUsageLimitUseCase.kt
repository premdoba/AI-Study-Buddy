package com.nextgendevs.quicknotes.domain.usecase

import com.nextgendevs.quicknotes.domain.repository.UsageRepository

class CheckUsageLimitUseCase(
    private val repository: UsageRepository
) {

    suspend operator fun invoke(): Boolean {
        return repository.isUsageAllowed()
    }

}