package com.example.kotlintemplate.data.repository

import com.example.kotlintemplate.domain.model.Difficulty
import com.example.kotlintemplate.domain.model.Question

interface QuestionRepository {
    fun getQuestions(difficulty: Difficulty, count: Int = 10): List<Question>
}
