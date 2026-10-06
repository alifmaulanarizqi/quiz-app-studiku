package com.example.kotlintemplate.domain.repository

import com.example.kotlintemplate.data.datasource.QuestionLocalDataSource
import com.example.kotlintemplate.data.repository.QuestionRepository
import com.example.kotlintemplate.domain.model.Difficulty
import com.example.kotlintemplate.domain.model.Question
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestionRepositoryImpl @Inject constructor(
    private val dataSource: QuestionLocalDataSource
) : QuestionRepository {

    override fun getQuestions(difficulty: Difficulty, count: Int): List<Question> {
        val filtered = dataSource.getAllQuestions().filter { it.difficulty == difficulty }
        val selected = filtered.shuffled().take(count)

        return selected.map { question ->
            val correctAnswerText = question.options[question.correctAnswerIndex]
            val shuffledOptions = question.options.shuffled()
            val newCorrectIndex = shuffledOptions.indexOf(correctAnswerText)

            question.copy(
                options = shuffledOptions,
                correctAnswerIndex = newCorrectIndex
            )
        }
    }
}
