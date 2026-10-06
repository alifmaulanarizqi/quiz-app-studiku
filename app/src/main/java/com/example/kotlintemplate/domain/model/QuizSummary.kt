package com.example.kotlintemplate.domain.model

data class QuizSummary(
    val difficulty: Difficulty,
    val score: Int,
    val correctCount: Int,
    val totalQuestions: Int = 10,
    val isNewBestScore: Boolean = false,
    val previousBestScore: Int = 0
)
