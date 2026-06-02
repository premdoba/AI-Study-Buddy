package com.example.quicknotes.domain.usecase

import com.example.quicknotes.domain.repository.UsageRepository

class IncrementUsageUseCase(
    private val repository: UsageRepository
) {

    suspend operator fun invoke() {
        repository.increment()
    }

}