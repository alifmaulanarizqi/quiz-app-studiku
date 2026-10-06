package com.example.kotlintemplate.ui.feature.home

import com.example.kotlintemplate.domain.model.BestScores

data class HomeState(
    val isLoading: Boolean = false,
    val bestScores: BestScores = BestScores()
)