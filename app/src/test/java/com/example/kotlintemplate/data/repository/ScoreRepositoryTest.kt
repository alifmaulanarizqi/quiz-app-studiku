package com.example.kotlintemplate.data.repository

import android.content.SharedPreferences
import com.example.kotlintemplate.data.datasource.ScorePreferences
import com.example.kotlintemplate.domain.model.BestScores
import com.example.kotlintemplate.domain.model.Difficulty
import com.example.kotlintemplate.domain.repository.ScoreRepositoryImpl
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ScoreRepositoryTest {

    private lateinit var fakeSharedPreferences: FakeSharedPreferences
    private lateinit var scorePreferences: ScorePreferences
    private lateinit var repository: ScoreRepositoryImpl

    @Before
    fun setUp() {
        fakeSharedPreferences = FakeSharedPreferences()
        scorePreferences = ScorePreferences(fakeSharedPreferences)
        repository = ScoreRepositoryImpl(scorePreferences)
    }

    @Test
    fun saveBestScoreIfHigher_savesWhenScoreIsHigher() {
        assertEquals(0, repository.getBestScore(Difficulty.EASY))

        val result = repository.saveBestScoreIfHigher(Difficulty.EASY, 80)
        assertTrue(result)
        assertEquals(80, repository.getBestScore(Difficulty.EASY))

        val result2 = repository.saveBestScoreIfHigher(Difficulty.EASY, 100)
        assertTrue(result2)
        assertEquals(100, repository.getBestScore(Difficulty.EASY))
    }

    @Test
    fun saveBestScoreIfHigher_doesNotSaveWhenScoreIsLowerOrEqual() {
        repository.saveBestScoreIfHigher(Difficulty.MEDIUM, 80)
        assertEquals(80, repository.getBestScore(Difficulty.MEDIUM))

        val lowerResult = repository.saveBestScoreIfHigher(Difficulty.MEDIUM, 60)
        assertFalse(lowerResult)
        assertEquals(80, repository.getBestScore(Difficulty.MEDIUM))

        val equalResult = repository.saveBestScoreIfHigher(Difficulty.MEDIUM, 80)
        assertFalse(equalResult)
        assertEquals(80, repository.getBestScore(Difficulty.MEDIUM))
    }

    @Test
    fun getBestScores_returnsScoresForEachDifficulty() {
        repository.saveBestScoreIfHigher(Difficulty.EASY, 90)
        repository.saveBestScoreIfHigher(Difficulty.MEDIUM, 70)
        repository.saveBestScoreIfHigher(Difficulty.HARD, 50)

        val expected = BestScores(easy = 90, medium = 70, hard = 50)
        assertEquals(expected, repository.getBestScores())
    }
}

private class FakeSharedPreferences : SharedPreferences {
    private val data = mutableMapOf<String, Any?>()

    override fun getAll(): MutableMap<String, *> = data.toMutableMap()
    override fun getString(key: String?, defValue: String?): String? = data[key] as? String ?: defValue
    override fun getStringSet(key: String?, defValues: MutableSet<String>?): MutableSet<String>? =
        (data[key] as? Set<*>)?.filterIsInstance<String>()?.toMutableSet() ?: defValues
    override fun getInt(key: String?, defValue: Int): Int = (data[key] as? Int) ?: defValue
    override fun getLong(key: String?, defValue: Long): Long = (data[key] as? Long) ?: defValue
    override fun getFloat(key: String?, defValue: Float): Float = (data[key] as? Float) ?: defValue
    override fun getBoolean(key: String?, defValue: Boolean): Boolean = (data[key] as? Boolean) ?: defValue
    override fun contains(key: String?): Boolean = data.containsKey(key)
    override fun edit(): SharedPreferences.Editor = FakeEditor(data)
    override fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {}
    override fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {}

    private class FakeEditor(private val backingData: MutableMap<String, Any?>) : SharedPreferences.Editor {
        private val pendingChanges = mutableMapOf<String, Any?>()
        private val pendingRemovals = mutableSetOf<String>()
        private var clear = false

        override fun putString(key: String?, value: String?): SharedPreferences.Editor = apply {
            key?.let { pendingChanges[it] = value }
        }
        override fun putStringSet(key: String?, values: MutableSet<String>?): SharedPreferences.Editor = apply {
            key?.let { pendingChanges[it] = values }
        }
        override fun putInt(key: String?, value: Int): SharedPreferences.Editor = apply {
            key?.let { pendingChanges[it] = value }
        }
        override fun putLong(key: String?, value: Long): SharedPreferences.Editor = apply {
            key?.let { pendingChanges[it] = value }
        }
        override fun putFloat(key: String?, value: Float): SharedPreferences.Editor = apply {
            key?.let { pendingChanges[it] = value }
        }
        override fun putBoolean(key: String?, value: Boolean): SharedPreferences.Editor = apply {
            key?.let { pendingChanges[it] = value }
        }
        override fun remove(key: String?): SharedPreferences.Editor = apply {
            key?.let { pendingRemovals.add(it) }
        }
        override fun clear(): SharedPreferences.Editor = apply {
            clear = true
        }
        override fun commit(): Boolean {
            apply()
            return true
        }
        override fun apply() {
            if (clear) {
                backingData.clear()
            }
            for (key in pendingRemovals) {
                backingData.remove(key)
            }
            backingData.putAll(pendingChanges)
        }
    }
}
