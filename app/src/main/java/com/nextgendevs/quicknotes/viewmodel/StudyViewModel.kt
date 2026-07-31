package com.nextgendevs.quicknotes.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgendevs.quicknotes.domain.model.StudyHistory
import com.nextgendevs.quicknotes.domain.model.QuizHistory
import com.nextgendevs.quicknotes.domain.model.StudyNotes
import com.nextgendevs.quicknotes.domain.repository.StudyRepository
import com.nextgendevs.quicknotes.domain.repository.QuizRepository
import com.nextgendevs.quicknotes.domain.repository.AiRepository
import com.nextgendevs.quicknotes.domain.repository.UsageRepository
import com.nextgendevs.quicknotes.domain.usecase.CheckUsageLimitUseCase
import com.nextgendevs.quicknotes.domain.usecase.IncrementUsageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    data class Success(val notes: StudyNotes) : UiState()
    data class Error(val message: String) : UiState()
}

@HiltViewModel
class StudyViewModel @Inject constructor(
    private val studyRepo: StudyRepository,
    private val quizRepo: QuizRepository,
    private val aiRepo: AiRepository,
    private val usageRepository: UsageRepository
) : ViewModel() {

    val historyList = studyRepo.getAllHistory()
    val quizHistoryList = quizRepo.getAllQuizHistory()

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()


    private val checkUsageLimitUseCase =
        CheckUsageLimitUseCase(usageRepository)

    private val incrementUsageUseCase =
        IncrementUsageUseCase(usageRepository)

    private val _remainingUsage = MutableStateFlow<Long?>(null)

    val remainingUsage = _remainingUsage.asStateFlow()

    var lastInputText: String = ""
    var lastLevelText: String = ""
    var lastMcqDifficulty: String = "Medium"

    private val chatHistory = mutableListOf<String>()

    fun generateStudyMaterial(input: String, educationLevel: String) {
        lastInputText = input
        lastLevelText = educationLevel

        viewModelScope.launch {
            if (!checkUsageLimitUseCase()) {

                _uiState.value =
                    UiState.Error("Free limit reached")

                return@launch
            }
            _uiState.value = UiState.Loading

            try {
                val notes = aiRepo.generateStudyMaterial(input, educationLevel)

                if (notes == null) {
                    _uiState.value = UiState.Error("Empty response received from AI. Please refresh your input.")
                } else {
                    incrementUsageUseCase()
                    loadRemainingUsage()
                    _uiState.value = UiState.Success(notes)

                    chatHistory.clear()
                    chatHistory.add("CONTENT:\n${input.take(8000)}")
                    chatHistory.add("AI STUDY MATERIAL GENERATED")

                    studyRepo.insertHistory(
                        StudyHistory(
                            topic = input.take(35),
                            educationLevel = educationLevel,
                            shortNotes = notes.shortNotes,
                            questions = notes.importantQuestions,
                            mcqsRaw = notes.mcqsRaw,
                            summary = notes.summary,
                            originalInput = input.take(8000)
                        )
                    )
                }

            } catch (e: Exception) {
                Log.e("GEMINI_ERROR", "Gemini API Failed", e)
                _uiState.value = UiState.Error("Server is busy. Please try again.")
            }
        }
    }

    suspend fun getHistoryById(id: Int): StudyHistory? {
        return studyRepo.getById(id)
    }

    fun askDoubt(question: String, onResult: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val currentState = _uiState.value
                val contextText = if (currentState is UiState.Success) {
                    """
                    Short Notes:
                    ${currentState.notes.shortNotes}
                    Summary:
                    ${currentState.notes.summary}
                    Important Questions:
                    ${currentState.notes.importantQuestions}
                    """.trimIndent()
                } else ""

                val historyText = chatHistory.joinToString("\n\n")

                val responseText = aiRepo.askDoubt(question, lastLevelText, contextText, historyText)

                if (responseText.isNotBlank() && !responseText.startsWith("Error:")) {
                    chatHistory.add("USER QUESTION:\n$question")
                    chatHistory.add("AI ANSWER:\n$responseText")
                    onResult(responseText)
                } else {
                    onResult(responseText.ifBlank { "No response received. Please try again." })
                }
            } catch (e: Exception) {
                Log.e("DOUBT_ERROR", "Ask Doubt Failed", e)
                onResult("Error: Server is too busy currently, please try again later after some time.")
            }
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            studyRepo.deleteAll()
        }
    }

    fun deleteHistoryItem(id: Int) {
        viewModelScope.launch {
            studyRepo.deleteById(id)
        }
    }

    fun insertHistoryItem(item: StudyHistory) {
        viewModelScope.launch {
            studyRepo.insertHistory(item)
        }
    }

    fun refreshMcqs(input: String, difficulty: String) {
        lastInputText = input
        lastMcqDifficulty = difficulty
        viewModelScope.launch {
            try {
                val result = aiRepo.generateMcqs(input, lastLevelText, difficulty)
                
                if (result != null) {
                    val (newMcqs, mcqRaw) = result
                    val currentState = _uiState.value
                    if (currentState is UiState.Success) {
                        val updatedNotes = currentState.notes.copy(mcqs = newMcqs, mcqsRaw = mcqRaw)
                        _uiState.value = UiState.Success(updatedNotes)
                    }
                }
            } catch (e: Exception) {
                Log.e("MCQ_REFRESH_ERROR", "Refresh MCQs Failed", e)
            }
        }
    }

    fun loadRemainingUsage() {

        viewModelScope.launch {

            _remainingUsage.value =
                usageRepository.getRemainingUsage()
        }
    }

    fun clearSession() {
        _uiState.value = UiState.Idle
        chatHistory.clear()
        lastInputText = ""
        lastLevelText = ""
        lastMcqDifficulty = "Medium"
    }

    fun loadHistoryNotes(notes: StudyNotes) {
        _uiState.value = UiState.Success(notes)
    }

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    fun saveQuizResult(title: String, score: Int, totalQuestions: Int, quizJson: String) {
        viewModelScope.launch {
            quizRepo.insertQuiz(
                QuizHistory(
                    title = title,
                    date = System.currentTimeMillis(),
                    score = score,
                    totalQuestions = totalQuestions,
                    quizJson = quizJson
                )
            )
        }
    }

    fun deleteQuiz(id: Int) {
        viewModelScope.launch {
            quizRepo.deleteQuiz(id)
        }
    }

    fun clearQuizHistory() {
        viewModelScope.launch {
            quizRepo.clearQuizHistory()
        }
    }

    fun saveQuiz(quiz: QuizHistory) {
        viewModelScope.launch {
            quizRepo.insertQuiz(quiz)
        }
    }

    fun getQuizById(id: Int): Flow<QuizHistory?> {
        return quizRepo.getQuizById(id)
    }
}