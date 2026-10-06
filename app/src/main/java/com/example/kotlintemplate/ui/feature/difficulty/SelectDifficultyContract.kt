package com.example.kotlintemplate.ui.feature.difficulty

import com.example.kotlintemplate.domain.model.BestScores

data class SelectDifficultyState(
    val isLoading: Boolean = false,
    val bestScores: BestScores = BestScores()
)
