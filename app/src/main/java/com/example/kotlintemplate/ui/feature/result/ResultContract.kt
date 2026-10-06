package com.example.kotlintemplate.ui.feature.result

import com.example.kotlintemplate.domain.model.Difficulty

data class ResultUiState(
    val difficulty: Difficulty = Difficulty.EASY,
    val score: Int = 0,
    val correctCount: Int = 0,
    val totalQuestions: Int = 10,
    val isNewBestScore: Boolean = false,
    val previousBestScore: Int = 0,
    val currentBestScore: Int = 0
) {
    val incorrectCount: Int
        get() = (totalQuestions - correctCount).coerceAtLeast(0)
}
