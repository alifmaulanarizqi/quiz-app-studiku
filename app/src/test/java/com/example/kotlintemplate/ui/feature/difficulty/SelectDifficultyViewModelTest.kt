package com.example.kotlintemplate.ui.feature.difficulty

import com.example.kotlintemplate.domain.model.BestScores
import com.example.kotlintemplate.domain.model.Difficulty
import com.example.kotlintemplate.data.repository.ScoreRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class SelectDifficultyViewModelTest {

    private class FakeScoreRepository : ScoreRepository {
        var scoresToReturn: BestScores = BestScores()

        override fun getBestScores(): BestScores = scoresToReturn
        override fun getBestScore(difficulty: Difficulty): Int = 0
        override fun saveBestScoreIfHigher(difficulty: Difficulty, score: Int): Boolean = false
    }

    @Test
    fun init_loadsBestScoresSuccessfully() {
        val fakeRepo = FakeScoreRepository()
        fakeRepo.scoresToReturn = BestScores(easy = 70, medium = 50, hard = 30)
        val viewModel = SelectDifficultyViewModel(fakeRepo)

        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertEquals(70, state.bestScores.easy)
        assertEquals(50, state.bestScores.medium)
        assertEquals(30, state.bestScores.hard)
    }

    @Test
    fun loadBestScores_refreshesState() {
        val fakeRepo = FakeScoreRepository()
        fakeRepo.scoresToReturn = BestScores(easy = 10, medium = 20, hard = 30)
        val viewModel = SelectDifficultyViewModel(fakeRepo)

        assertEquals(10, viewModel.state.value.bestScores.easy)

        fakeRepo.scoresToReturn = BestScores(easy = 90, medium = 80, hard = 70)
        viewModel.loadBestScores()

        assertEquals(90, viewModel.state.value.bestScores.easy)
        assertEquals(80, viewModel.state.value.bestScores.medium)
        assertEquals(70, viewModel.state.value.bestScores.hard)
    }
}
