package com.example.kotlintemplate.ui.feature.difficulty

import androidx.lifecycle.ViewModel
import com.example.kotlintemplate.data.repository.ScoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

// ponytail: Direct repository injection in ViewModel, eliminating redundant 1-line UseCase boilerplate.
@HiltViewModel
class SelectDifficultyViewModel @Inject constructor(
    private val scoreRepository: ScoreRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SelectDifficultyState())
    val state: StateFlow<SelectDifficultyState> = _state.asStateFlow()

    init {
        loadBestScores()
    }

    fun loadBestScores() {
        val scores = scoreRepository.getBestScores()
        _state.update { it.copy(bestScores = scores, isLoading = false) }
    }
}
