package com.example.kotlintemplate.ui.feature.quiz

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun QuizRoute(
    onQuizFinished: (difficulty: String, score: Int, correctCount: Int) -> Unit,
    onBackClick: () -> Unit,
    viewModel: QuizViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isFinished) {
        if (state.isFinished) {
            onQuizFinished(
                state.difficulty.key,
                state.score,
                state.correctCount
            )
        }
    }

    QuizScreen(
        state = state,
        onSelectOption = viewModel::selectOption,
        onBackClick = onBackClick
    )
}
