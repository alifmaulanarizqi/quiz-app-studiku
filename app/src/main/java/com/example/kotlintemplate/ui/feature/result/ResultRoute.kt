package com.example.kotlintemplate.ui.feature.result

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ResultRoute(
    onPlayAgain: () -> Unit,
    onBackToHome: () -> Unit,
    viewModel: ResultViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ResultScreen(
        state = state,
        onPlayAgain = onPlayAgain,
        onBackToHome = onBackToHome
    )
}
