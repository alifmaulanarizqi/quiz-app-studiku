package com.example.kotlintemplate.data.repository

import com.example.kotlintemplate.data.datasource.QuestionLocalDataSource
import com.example.kotlintemplate.domain.model.Difficulty
import com.example.kotlintemplate.domain.repository.QuestionRepositoryImpl
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class QuestionRepositoryTest {

    private lateinit var dataSource: QuestionLocalDataSource
    private lateinit var repository: QuestionRepositoryImpl

    @Before
    fun setUp() {
        dataSource = QuestionLocalDataSource()
        repository = QuestionRepositoryImpl(dataSource)
    }

    @Test
    fun getQuestions_returnsExactRequestedCount() {
        val easyQuestions = repository.getQuestions(Difficulty.EASY, count = 10)
        assertEquals(10, easyQuestions.size)

        val customCount = repository.getQuestions(Difficulty.MEDIUM, count = 5)
        assertEquals(5, customCount.size)
    }

    @Test
    fun getQuestions_returnsQuestionsMatchingDifficulty() {
        for (difficulty in Difficulty.entries) {
            val questions = repository.getQuestions(difficulty, count = 10)
            assertTrue(questions.isNotEmpty())
            assertTrue(questions.all { it.difficulty == difficulty })
        }
    }

    @Test
    fun getQuestions_preservesCorrectAnswerAfterOptionsShuffle() {
        val originalQuestions = dataSource.getAllQuestions().associateBy { it.id }

        for (difficulty in Difficulty.entries) {
            val questions = repository.getQuestions(difficulty, count = 15)
            for (question in questions) {
                val original = originalQuestions[question.id]
                    ?: error("Question with id ${question.id} not found in original data source")
                val expectedAnswerText = original.options[original.correctAnswerIndex]
                val actualAnswerText = question.options[question.correctAnswerIndex]

                assertEquals(expectedAnswerText, actualAnswerText)
                assertEquals(original.options.toSet(), question.options.toSet())
                assertEquals(4, question.options.size)
                assertTrue(question.correctAnswerIndex in question.options.indices)
            }
        }
    }

    @Test
    fun getQuestions_shufflesQuestions() {
        val call1 = repository.getQuestions(Difficulty.EASY, count = 10).map { it.id }
        val call2 = repository.getQuestions(Difficulty.EASY, count = 10).map { it.id }
        val call3 = repository.getQuestions(Difficulty.EASY, count = 10).map { it.id }

        val isDifferent = call1 != call2 || call2 != call3 || call1 != call3
        assertTrue(
            "Subsequent calls to getQuestions should return shuffled or different order/selection",
            isDifferent
        )
    }

    @Test
    fun dataSource_containsAtLeast15QuestionsPerDifficulty() {
        val allQuestions = dataSource.getAllQuestions()
        for (difficulty in Difficulty.entries) {
            val count = allQuestions.count { it.difficulty == difficulty }
            assertTrue(
                "Difficulty $difficulty should have at least 15 questions, found $count",
                count >= 15
            )
        }
    }
}
