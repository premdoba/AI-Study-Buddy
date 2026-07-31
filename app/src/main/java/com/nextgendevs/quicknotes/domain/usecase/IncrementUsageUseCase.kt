package com.nextgendevs.quicknotes.domain.usecase

import com.nextgendevs.quicknotes.domain.repository.UsageRepository

class IncrementUsageUseCase(
    private val repository: UsageRepository
) {

    suspend operator fun invoke() {
        repository.increment()
    }

}