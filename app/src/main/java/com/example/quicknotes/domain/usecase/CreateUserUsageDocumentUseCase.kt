package com.example.quicknotes.domain.usecase

import AuthRepository

class CreateUserUsageDocumentUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke() {
        repository.createUserUsageDocument()
    }
}