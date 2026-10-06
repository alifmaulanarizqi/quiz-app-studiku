package com.example.kotlintemplate.ui.feature.result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.kotlintemplate.domain.model.Difficulty
import com.example.kotlintemplate.data.repository.ScoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

// ponytail: Direct repository injection in ViewModel, eliminating redundant 1-line UseCase boilerplate.
@HiltViewModel
class ResultViewModel @Inject constructor(
    private val scoreRepository: ScoreRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val difficultyKey: String? = savedStateHandle["difficulty"]
    private val difficulty: Difficulty = difficultyKey?.let { Difficulty.fromKey(it) } ?: Difficulty.EASY

    private val score: Int = savedStateHandle.get<Int>("score")
        ?: savedStateHandle.get<String>("score")?.toIntOrNull()
        ?: 0

    private val correctCount: Int = savedStateHandle.get<Int>("correctCount")
        ?: savedStateHandle.get<String>("correctCount")?.toIntOrNull()
        ?: 0

    private val _state = MutableStateFlow(ResultUiState())
    val state: StateFlow<ResultUiState> = _state.asStateFlow()

    init {
        evaluateAndSaveScore()
    }

    private fun evaluateAndSaveScore() {
        val previousBest = scoreRepository.getBestScore(difficulty)
        val isNewBest = scoreRepository.saveBestScoreIfHigher(difficulty, score)
        val currentBest = if (isNewBest) score else previousBest

        _state.value = ResultUiState(
            difficulty = difficulty,
            score = score,
            correctCount = correctCount,
            totalQuestions = 10,
            isNewBestScore = isNewBest,
            previousBestScore = previousBest,
            currentBestScore = currentBest
        )
    }
}
