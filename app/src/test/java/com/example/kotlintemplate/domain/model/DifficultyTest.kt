package com.example.kotlintemplate.domain.model

import org.junit.Assert.assertEquals
import org.junit.Test

class DifficultyTest {

    @Test
    fun `fromKey returns EASY for easy key`() {
        assertEquals(Difficulty.EASY, Difficulty.fromKey("easy"))
        assertEquals(Difficulty.EASY, Difficulty.fromKey("Easy"))
        assertEquals(Difficulty.EASY, Difficulty.fromKey("EASY"))
    }

    @Test
    fun `fromKey returns MEDIUM for medium key`() {
        assertEquals(Difficulty.MEDIUM, Difficulty.fromKey("medium"))
        assertEquals(Difficulty.MEDIUM, Difficulty.fromKey("Medium"))
        assertEquals(Difficulty.MEDIUM, Difficulty.fromKey("MEDIUM"))
    }

    @Test
    fun `fromKey returns HARD for hard key`() {
        assertEquals(Difficulty.HARD, Difficulty.fromKey("hard"))
        assertEquals(Difficulty.HARD, Difficulty.fromKey("Hard"))
        assertEquals(Difficulty.HARD, Difficulty.fromKey("HARD"))
    }

    @Test
    fun `fromKey falls back to EASY for invalid or unknown key`() {
        assertEquals(Difficulty.EASY, Difficulty.fromKey("unknown"))
        assertEquals(Difficulty.EASY, Difficulty.fromKey(""))
    }

    @Test
    fun `display names and keys match expectations`() {
        assertEquals("easy", Difficulty.EASY.key)
        assertEquals("Easy", Difficulty.EASY.displayName)
        assertEquals("medium", Difficulty.MEDIUM.key)
        assertEquals("Medium", Difficulty.MEDIUM.displayName)
        assertEquals("hard", Difficulty.HARD.key)
        assertEquals("Hard", Difficulty.HARD.displayName)
    }
}
