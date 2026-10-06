package com.example.kotlintemplate.domain.model

enum class Difficulty(val key: String, val displayName: String) {
    EASY("easy", "Easy"),
    MEDIUM("medium", "Medium"),
    HARD("hard", "Hard");

    companion object {
        fun fromKey(key: String): Difficulty =
            entries.firstOrNull { it.key.equals(key, ignoreCase = true) } ?: EASY
    }
}
