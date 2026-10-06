package com.example.kotlintemplate.ui.feature.quiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlintemplate.data.repository.QuestionRepository
import com.example.kotlintemplate.domain.model.Difficulty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// ponytail: Direct repository injection in ViewModel, eliminating redundant 1-line UseCase boilerplate.
@HiltViewModel
class QuizViewModel @Inject constructor(
    private val questionRepository: QuestionRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val difficultyKey: String? = savedStateHandle["difficulty"]
    private val difficulty: Difficulty = difficultyKey?.let { Difficulty.fromKey(it) } ?: Difficulty.EASY

    private val _state = MutableStateFlow(QuizUiState(difficulty = difficulty))
    val state: StateFlow<QuizUiState> = _state.asStateFlow()

    private var timerJob: Job? = null

    init {
        loadQuestions()
    }

    private fun loadQuestions() {
        val questions = questionRepository.getQuestions(difficulty, 10)
        _state.update {
            it.copy(
                questions = questions,
                currentIndex = 0,
                score = 0,
                correctCount = 0,
                timeRemainingSeconds = 15,
                selectedOptionIndex = null,
                isAnswerLocked = false,
                isFinished = questions.isEmpty()
            )
        }
        if (questions.isNotEmpty()) {
            startTimer()
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_state.value.timeRemainingSeconds > 0) {
                delay(1000)
                _state.update { it.copy(timeRemainingSeconds = it.timeRemainingSeconds - 1) }
            }
            onTimeout()
        }
    }

    private fun onTimeout() {
        timerJob?.cancel()
        val currentState = _state.value
        if (currentState.currentIndex + 1 < currentState.questions.size) {
            _state.update {
                it.copy(
                    currentIndex = it.currentIndex + 1,
                    timeRemainingSeconds = 15,
                    selectedOptionIndex = null,
                    isAnswerLocked = false
                )
            }
            startTimer()
        } else {
            _state.update {
                it.copy(
                    isFinished = true,
                    isAnswerLocked = true
                )
            }
        }
    }

    fun selectOption(optionIndex: Int) {
        val currentState = _state.value
        if (currentState.isAnswerLocked || currentState.isFinished || currentState.currentQuestion == null) {
            return
        }

        timerJob?.cancel()

        val isCorrect = optionIndex == currentState.currentQuestion?.correctAnswerIndex
        val addedScore = if (isCorrect) 10 else 0
        val addedCorrectCount = if (isCorrect) 1 else 0

        _state.update {
            it.copy(
                selectedOptionIndex = optionIndex,
                isAnswerLocked = true,
                score = it.score + addedScore,
                correctCount = it.correctCount + addedCorrectCount
            )
        }

        viewModelScope.launch {
            delay(600)
            val updatedState = _state.value
            if (updatedState.currentIndex + 1 < updatedState.questions.size) {
                _state.update {
                    it.copy(
                        currentIndex = it.currentIndex + 1,
                        timeRemainingSeconds = 15,
                        selectedOptionIndex = null,
                        isAnswerLocked = false
                    )
                }
                startTimer()
            } else {
                _state.update {
                    it.copy(isFinished = true)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
