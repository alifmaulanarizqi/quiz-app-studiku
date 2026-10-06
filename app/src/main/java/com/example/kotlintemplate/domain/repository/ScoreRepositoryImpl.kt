package com.example.kotlintemplate.domain.repository

import com.example.kotlintemplate.data.datasource.ScorePreferences
import com.example.kotlintemplate.data.repository.ScoreRepository
import com.example.kotlintemplate.domain.model.BestScores
import com.example.kotlintemplate.domain.model.Difficulty
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoreRepositoryImpl @Inject constructor(
    private val preferences: ScorePreferences
) : ScoreRepository {

    override fun getBestScores(): BestScores = preferences.getBestScores()

    override fun getBestScore(difficulty: Difficulty): Int = preferences.getBestScore(difficulty)

    override fun saveBestScoreIfHigher(difficulty: Difficulty, score: Int): Boolean =
        preferences.saveBestScoreIfHigher(difficulty, score)
}
