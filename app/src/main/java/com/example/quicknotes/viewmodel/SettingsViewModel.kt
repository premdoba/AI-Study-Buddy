package com.example.quicknotes.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quicknotes.data.repository.SettingsRepositoryImpl
import com.example.quicknotes.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel()
{

    val education =
        repository.educationFlow.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            "Graduation"
        )

    val mcq =
        repository.mcqFlow.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            "Medium"
        )

    val theme =
        repository.themeFlow.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            "Auto"
        )

    fun saveEducation(value: String) {
        viewModelScope.launch {
            repository.saveEducation(value)
        }
    }

    fun saveMcq(value: String) {
        viewModelScope.launch {
            repository.saveMcq(value)
        }
    }

    fun saveTheme(value: String) {
        viewModelScope.launch {
            repository.saveTheme(value)
        }
    }
}