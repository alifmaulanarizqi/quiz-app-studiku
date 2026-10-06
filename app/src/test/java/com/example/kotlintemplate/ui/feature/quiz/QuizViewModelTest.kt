package com.example.kotlintemplate.ui.feature.quiz

import androidx.lifecycle.SavedStateHandle
import com.example.kotlintemplate.domain.model.Difficulty
import com.example.kotlintemplate.domain.model.Question
import com.example.kotlintemplate.data.repository.QuestionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class QuizViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private class FakeQuestionRepository(
        private val questions: List<Question>
    ) : QuestionRepository {
        override fun getQuestions(difficulty: Difficulty, count: Int): List<Question> =
            questions.filter { it.difficulty == difficulty }.take(count)
    }

    private fun createMockQuestions(
        count: Int = 10,
        difficulty: Difficulty = Difficulty.EASY
    ): List<Question> {
        return (1..count).map { index ->
            Question(
                id = index,
                text = "Question $index?",
                options = listOf("Answer A", "Answer B", "Answer C", "Answer D"),
                correctAnswerIndex = 0,
                difficulty = difficulty
            )
        }
    }

    private fun createViewModel(
        difficulty: Difficulty = Difficulty.EASY,
        questionsCount: Int = 10
    ): QuizViewModel {
        val mockQuestions = createMockQuestions(count = questionsCount, difficulty = difficulty)
        val repository = FakeQuestionRepository(mockQuestions)
        val savedStateHandle = SavedStateHandle(mapOf("difficulty" to difficulty.key))
        return QuizViewModel(repository, savedStateHandle)
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun init_loadsQuestionsAndStartsTimer() = runTest(testDispatcher) {
        val viewModel = createViewModel()
        runCurrent()

        val state = viewModel.state.value
        assertEquals(10, state.questions.size)
        assertEquals(0, state.currentIndex)
        assertEquals(0, state.score)
        assertEquals(0, state.correctCount)
        assertEquals(15, state.timeRemainingSeconds)
        assertNull(state.selectedOptionIndex)
        assertFalse(state.isAnswerLocked)
        assertFalse(state.isFinished)
        assertEquals("Question 1?", state.currentQuestion?.text)
        assertEquals("1/10", state.questionNumberDisplay)
        assertEquals(0.1f, state.progress, 0.001f)

        // Advance 1 second
        advanceTimeBy(1000)
        runCurrent()
        assertEquals(14, viewModel.state.value.timeRemainingSeconds)

        // Advance another 4 seconds
        advanceTimeBy(4000)
        runCurrent()
        assertEquals(10, viewModel.state.value.timeRemainingSeconds)
    }

    @Test
    fun selectOption_correctAnswer_incrementsScoreAndCorrectCount() = runTest(testDispatcher) {
        val viewModel = createViewModel()
        runCurrent()

        viewModel.selectOption(0)
        runCurrent()

        val stateAfterSelection = viewModel.state.value
        assertEquals(0, stateAfterSelection.selectedOptionIndex)
        assertTrue(stateAfterSelection.isAnswerLocked)
        assertEquals(10, stateAfterSelection.score)
        assertEquals(1, stateAfterSelection.correctCount)

        // Advance past delay (600ms)
        advanceTimeBy(600)
        runCurrent()

        val stateAfterAdvance = viewModel.state.value
        assertEquals(1, stateAfterAdvance.currentIndex)
        assertNull(stateAfterAdvance.selectedOptionIndex)
        assertFalse(stateAfterAdvance.isAnswerLocked)
        assertEquals(15, stateAfterAdvance.timeRemainingSeconds)
        assertEquals(10, stateAfterAdvance.score)
        assertEquals(1, stateAfterAdvance.correctCount)
    }

    @Test
    fun selectOption_wrongAnswer_doesNotIncrementScore() = runTest(testDispatcher) {
        val viewModel = createViewModel()
        runCurrent()

        // Option 1 is incorrect (correct is 0)
        viewModel.selectOption(1)
        runCurrent()

        val stateAfterSelection = viewModel.state.value
        assertEquals(1, stateAfterSelection.selectedOptionIndex)
        assertTrue(stateAfterSelection.isAnswerLocked)
        assertEquals(0, stateAfterSelection.score)
        assertEquals(0, stateAfterSelection.correctCount)

        // Advance past 600ms
        advanceTimeBy(600)
        runCurrent()

        val stateAfterAdvance = viewModel.state.value
        assertEquals(1, stateAfterAdvance.currentIndex)
        assertNull(stateAfterAdvance.selectedOptionIndex)
        assertFalse(stateAfterAdvance.isAnswerLocked)
        assertEquals(15, stateAfterAdvance.timeRemainingSeconds)
        assertEquals(0, stateAfterAdvance.score)
        assertEquals(0, stateAfterAdvance.correctCount)
    }

    @Test
    fun timeout_advancesToNextQuestionWithoutScore() = runTest(testDispatcher) {
        val viewModel = createViewModel()
        runCurrent()

        assertEquals(0, viewModel.state.value.currentIndex)
        assertEquals(0, viewModel.state.value.score)

        // Advance 15 seconds to trigger timeout
        advanceTimeBy(15000)
        runCurrent()

        val state = viewModel.state.value
        assertEquals(1, state.currentIndex)
        assertEquals(0, state.score)
        assertEquals(0, state.correctCount)
        assertEquals(15, state.timeRemainingSeconds)
        assertNull(state.selectedOptionIndex)
        assertFalse(state.isAnswerLocked)
    }

    @Test
    fun completingTenthQuestion_setsIsFinishedTrue() = runTest(testDispatcher) {
        val viewModel = createViewModel(questionsCount = 10)
        runCurrent()

        // Complete 9 questions
        for (i in 0 until 9) {
            viewModel.selectOption(0)
            advanceTimeBy(600)
            runCurrent()
        }

        assertEquals(9, viewModel.state.value.currentIndex)
        assertFalse(viewModel.state.value.isFinished)

        // Answer 10th question
        viewModel.selectOption(0)
        advanceTimeBy(600)
        runCurrent()

        val finalState = viewModel.state.value
        assertTrue(finalState.isFinished)
        assertEquals(100, finalState.score)
        assertEquals(10, finalState.correctCount)
    }
}
