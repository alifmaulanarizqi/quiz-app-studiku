package com.example.kotlintemplate.ui.feature.result

import androidx.lifecycle.SavedStateHandle
import com.example.kotlintemplate.domain.model.BestScores
import com.example.kotlintemplate.domain.model.Difficulty
import com.example.kotlintemplate.data.repository.ScoreRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ResultViewModelTest {

    private class FakeScoreRepository(
        var easyBest: Int = 0,
        var mediumBest: Int = 0,
        var hardBest: Int = 0
    ) : ScoreRepository {
        val savedRecords = mutableListOf<Pair<Difficulty, Int>>()

        override fun getBestScores(): BestScores = BestScores(easyBest, mediumBest, hardBest)

        override fun getBestScore(difficulty: Difficulty): Int = when (difficulty) {
            Difficulty.EASY -> easyBest
            Difficulty.MEDIUM -> mediumBest
            Difficulty.HARD -> hardBest
        }

        override fun saveBestScoreIfHigher(difficulty: Difficulty, score: Int): Boolean {
            savedRecords.add(difficulty to score)
            val current = getBestScore(difficulty)
            if (score > current) {
                when (difficulty) {
                    Difficulty.EASY -> easyBest = score
                    Difficulty.MEDIUM -> mediumBest = score
                    Difficulty.HARD -> hardBest = score
                }
                return true
            }
            return false
        }
    }

    private fun createViewModel(
        difficulty: Difficulty = Difficulty.EASY,
        score: Int = 0,
        correctCount: Int = 0,
        scoreRepository: ScoreRepository
    ): ResultViewModel {
        val savedStateHandle = SavedStateHandle(
            mapOf(
                "difficulty" to difficulty.key,
                "score" to score,
                "correctCount" to correctCount
            )
        )
        return ResultViewModel(
            scoreRepository = scoreRepository,
            savedStateHandle = savedStateHandle
        )
    }

    @Test
    fun init_whenScoreIsHigherThanBest_savesScoreAndSetsIsNewBestScoreTrue() {
        val repo = FakeScoreRepository(easyBest = 50)
        val viewModel = createViewModel(
            difficulty = Difficulty.EASY,
            score = 80,
            correctCount = 8,
            scoreRepository = repo
        )

        val state = viewModel.state.value
        assertTrue(state.isNewBestScore)
        assertEquals(50, state.previousBestScore)
        assertEquals(80, state.currentBestScore)
        assertEquals(80, repo.getBestScore(Difficulty.EASY))
        assertEquals(1, repo.savedRecords.size)
        assertEquals(Difficulty.EASY to 80, repo.savedRecords.first())
    }

    @Test
    fun init_whenScoreIsLowerOrEqual_doesNotSetIsNewBestScore() {
        val repo = FakeScoreRepository(mediumBest = 70)
        val viewModel = createViewModel(
            difficulty = Difficulty.MEDIUM,
            score = 60,
            correctCount = 6,
            scoreRepository = repo
        )

        val state = viewModel.state.value
        assertFalse(state.isNewBestScore)
        assertEquals(70, state.previousBestScore)
        assertEquals(70, state.currentBestScore)
        assertEquals(70, repo.getBestScore(Difficulty.MEDIUM))
    }

    @Test
    fun init_correctlyPopulatesStateFromNavArguments() {
        val repo = FakeScoreRepository(hardBest = 30)
        val viewModel = createViewModel(
            difficulty = Difficulty.HARD,
            score = 90,
            correctCount = 9,
            scoreRepository = repo
        )

        val state = viewModel.state.value
        assertEquals(Difficulty.HARD, state.difficulty)
        assertEquals(90, state.score)
        assertEquals(9, state.correctCount)
        assertEquals(10, state.totalQuestions)
        assertEquals(1, state.incorrectCount)
    }
}
