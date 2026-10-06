package com.example.kotlintemplate.ui.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeRoute(
    onStartQuiz: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    LifecycleResumeEffect(Unit) {
        viewModel.loadBestScores()
        onPauseOrDispose {}
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onStartQuiz = onStartQuiz
    )
}