package com.example.kotlintemplate.ui.navigation

object Routes {
    const val HOME = "home"
    const val SELECT_DIFFICULTY = "select_difficulty"
    const val QUIZ = "quiz/{difficulty}"
    fun quiz(difficulty: String): String = "quiz/$difficulty"
    const val RESULT = "result/{difficulty}/{score}/{correctCount}"
    fun result(difficulty: String, score: Int, correctCount: Int): String = "result/$difficulty/$score/$correctCount"
}