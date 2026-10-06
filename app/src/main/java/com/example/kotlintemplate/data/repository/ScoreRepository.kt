package com.example.kotlintemplate.data.repository

import com.example.kotlintemplate.domain.model.BestScores
import com.example.kotlintemplate.domain.model.Difficulty

interface ScoreRepository {
    fun getBestScores(): BestScores
    fun getBestScore(difficulty: Difficulty): Int
    fun saveBestScoreIfHigher(difficulty: Difficulty, score: Int): Boolean
}
