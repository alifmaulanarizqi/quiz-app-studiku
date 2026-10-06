package com.example.kotlintemplate.ui.feature.difficulty

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kotlintemplate.domain.model.Difficulty

@Composable
fun SelectDifficultyRoute(
    onBackClick: () -> Unit,
    onDifficultySelected: (Difficulty) -> Unit,
    viewModel: SelectDifficultyViewModel = hiltViewModel()
) {
    LifecycleResumeEffect(Unit) {
        viewModel.loadBestScores()
        onPauseOrDispose {}
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    SelectDifficultyScreen(
        bestScores = state.bestScores,
        onBackClick = onBackClick,
        onDifficultySelected = onDifficultySelected
    )
}
