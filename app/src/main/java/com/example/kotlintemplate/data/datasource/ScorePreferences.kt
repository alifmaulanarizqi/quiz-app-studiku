package com.example.kotlintemplate.data.datasource

import android.content.SharedPreferences
import com.example.kotlintemplate.domain.model.BestScores
import com.example.kotlintemplate.domain.model.Difficulty
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScorePreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    private fun getKey(difficulty: Difficulty): String = when (difficulty) {
        Difficulty.EASY -> KEY_BEST_SCORE_EASY
        Difficulty.MEDIUM -> KEY_BEST_SCORE_MEDIUM
        Difficulty.HARD -> KEY_BEST_SCORE_HARD
    }

    fun getBestScore(difficulty: Difficulty): Int {
        return sharedPreferences.getInt(getKey(difficulty), 0)
    }

    fun saveBestScoreIfHigher(difficulty: Difficulty, score: Int): Boolean {
        val currentBest = getBestScore(difficulty)
        if (score > currentBest) {
            sharedPreferences.edit()
                .putInt(getKey(difficulty), score)
                .apply()
            return true
        }
        return false
    }

    fun getBestScores(): BestScores {
        return BestScores(
            easy = getBestScore(Difficulty.EASY),
            medium = getBestScore(Difficulty.MEDIUM),
            hard = getBestScore(Difficulty.HARD)
        )
    }

    companion object {
        const val KEY_BEST_SCORE_EASY = "pref_best_score_easy"
        const val KEY_BEST_SCORE_MEDIUM = "pref_best_score_medium"
        const val KEY_BEST_SCORE_HARD = "pref_best_score_hard"
    }
}
