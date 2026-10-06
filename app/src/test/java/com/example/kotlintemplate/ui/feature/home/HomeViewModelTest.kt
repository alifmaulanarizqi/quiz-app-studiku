package com.example.kotlintemplate.ui.feature.home

import com.example.kotlintemplate.domain.model.BestScores
import com.example.kotlintemplate.domain.model.Difficulty
import com.example.kotlintemplate.data.repository.ScoreRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class HomeViewModelTest {

    private class FakeScoreRepository : ScoreRepository {
        var scoresToReturn: BestScores = BestScores()

        override fun getBestScores(): BestScores = scoresToReturn
        override fun getBestScore(difficulty: Difficulty): Int = 0
        override fun saveBestScoreIfHigher(difficulty: Difficulty, score: Int): Boolean = false
    }

    @Test
    fun init_loadsBestScoresSuccessfully() {
        val fakeRepo = FakeScoreRepository()
        fakeRepo.scoresToReturn = BestScores(easy = 100, medium = 80, hard = 60)
        val viewModel = HomeViewModel(fakeRepo)

        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertEquals(100, state.bestScores.easy)
        assertEquals(80, state.bestScores.medium)
        assertEquals(60, state.bestScores.hard)
    }

    @Test
    fun loadBestScores_updatesStateWhenRepositoryChanges() {
        val fakeRepo = FakeScoreRepository()
        fakeRepo.scoresToReturn = BestScores(easy = 0, medium = 0, hard = 0)
        val viewModel = HomeViewModel(fakeRepo)

        assertEquals(0, viewModel.state.value.bestScores.easy)

        fakeRepo.scoresToReturn = BestScores(easy = 50, medium = 30, hard = 10)
        viewModel.loadBestScores()

        assertEquals(50, viewModel.state.value.bestScores.easy)
        assertEquals(30, viewModel.state.value.bestScores.medium)
        assertEquals(10, viewModel.state.value.bestScores.hard)
    }
}
