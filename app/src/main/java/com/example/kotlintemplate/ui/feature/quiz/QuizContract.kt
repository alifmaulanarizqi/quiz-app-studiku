package com.example.kotlintemplate.ui.feature.quiz

import com.example.kotlintemplate.domain.model.Difficulty
import com.example.kotlintemplate.domain.model.Question

data class QuizUiState(
    val questions: List<Question> = emptyList(),
    val currentIndex: Int = 0,
    val score: Int = 0,
    val timeRemainingSeconds: Int = 15,
    val selectedOptionIndex: Int? = null,
    val isAnswerLocked: Boolean = false,
    val isFinished: Boolean = false,
    val correctCount: Int = 0,
    val difficulty: Difficulty = Difficulty.EASY
) {
    val currentQuestion: Question?
        get() = questions.getOrNull(currentIndex)

    val progress: Float
        get() = if (questions.isEmpty()) 0f else (currentIndex + 1).toFloat() / questions.size

    val questionNumberDisplay: String
        get() = "${currentIndex + 1}/${questions.size}"
}
