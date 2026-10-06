package com.example.kotlintemplate.ui.navigation

import org.junit.Assert.assertEquals
import org.junit.Test

class RoutesTest {

    @Test
    fun routes_constantsAndHelperFunctionsReturnExpectedPatterns() {
        assertEquals("home", Routes.HOME)
        assertEquals("select_difficulty", Routes.SELECT_DIFFICULTY)
        assertEquals("quiz/{difficulty}", Routes.QUIZ)
        assertEquals("quiz/medium", Routes.quiz("medium"))
        assertEquals("result/{difficulty}/{score}/{correctCount}", Routes.RESULT)
        assertEquals("result/hard/90/9", Routes.result("hard", 90, 9))
    }
}
