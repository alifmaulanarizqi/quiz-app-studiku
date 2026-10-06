package com.example.kotlintemplate.ui.feature.quiz

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kotlintemplate.domain.model.Difficulty
import com.example.kotlintemplate.domain.model.Question

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    state: QuizUiState,
    onSelectOption: (Int) -> Unit,
    onBackClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Kuis: ${state.difficulty.displayName}",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        val currentQuestion = state.currentQuestion

        if (currentQuestion == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // HUD Section
                QuizHud(
                    progress = state.progress,
                    questionDisplay = state.questionNumberDisplay,
                    timeRemainingSeconds = state.timeRemainingSeconds,
                    score = state.score
                )

                // Question Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Text(
                            text = currentQuestion.text,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // 4 Interactive Option Cards
                val optionLabels = listOf("A", "B", "C", "D")
                currentQuestion.options.forEachIndexed { index, optionText ->
                    val isCorrect = index == currentQuestion.correctAnswerIndex
                    val isSelected = state.selectedOptionIndex == index
                    val isAnswerLocked = state.isAnswerLocked

                    OptionCard(
                        label = optionLabels.getOrElse(index) { "${index + 1}" },
                        text = optionText,
                        index = index,
                        isLocked = isAnswerLocked,
                        isCorrect = isCorrect,
                        isSelected = isSelected,
                        onClick = { onSelectOption(index) }
                    )
                }
            }
        }
    }
}

@Composable
private fun QuizHud(
    progress: Float,
    questionDisplay: String,
    timeRemainingSeconds: Int,
    score: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Linear Progress Indicator
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
        )

        // HUD Info Row: Question counter, Timer chip, Current score
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Question count
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Text(
                    text = "Soal $questionDisplay",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

            // 15s Timer chip (red/warning when <= 5s)
            val isTimeCritical = timeRemainingSeconds <= 5
            val timerContainerColor by animateColorAsState(
                targetValue = if (isTimeCritical) {
                    MaterialTheme.colorScheme.errorContainer
                } else {
                    MaterialTheme.colorScheme.primaryContainer
                },
                label = "timerBgColor"
            )
            val timerContentColor by animateColorAsState(
                targetValue = if (isTimeCritical) {
                    MaterialTheme.colorScheme.onErrorContainer
                } else {
                    MaterialTheme.colorScheme.onPrimaryContainer
                },
                label = "timerTextColor"
            )

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = timerContainerColor
            ) {
                Text(
                    text = "⏱ ${timeRemainingSeconds}s",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = timerContentColor
                )
            }

            // Current score badge
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.tertiaryContainer
            ) {
                Text(
                    text = "★ $score pts",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        }
    }
}

@Composable
private fun OptionCard(
    label: String,
    text: String,
    index: Int,
    isLocked: Boolean,
    isCorrect: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val containerColor = when {
        !isLocked -> MaterialTheme.colorScheme.surface
        isCorrect -> Color(0xFFD4EDDA)
        isSelected && !isCorrect -> MaterialTheme.colorScheme.errorContainer
        else -> MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)
    }

    val contentColor = when {
        !isLocked -> MaterialTheme.colorScheme.onSurface
        isCorrect -> Color(0xFF155724)
        isSelected && !isCorrect -> MaterialTheme.colorScheme.onErrorContainer
        else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    }

    val borderColor = when {
        !isLocked -> MaterialTheme.colorScheme.outlineVariant
        isCorrect -> Color(0xFF28A745)
        isSelected && !isCorrect -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
    }

    val badgeBgColor = when {
        !isLocked -> MaterialTheme.colorScheme.primaryContainer
        isCorrect -> Color(0xFF28A745)
        isSelected && !isCorrect -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
    }

    val badgeTextColor = when {
        !isLocked -> MaterialTheme.colorScheme.onPrimaryContainer
        isCorrect || (isSelected && !isCorrect) -> Color.White
        else -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
    }

    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = !isLocked, onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = containerColor
        ),
        border = BorderStroke(if (isLocked && (isCorrect || isSelected)) 2.dp else 1.dp, borderColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(badgeBgColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = badgeTextColor
                )
            }

            Spacer(modifier = Modifier.size(14.dp))

            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = contentColor,
                modifier = Modifier.weight(1f)
            )

            if (isLocked) {
                if (isCorrect) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Benar",
                        tint = Color(0xFF28A745)
                    )
                } else if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Salah",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun QuizScreenPreview() {
    val sampleQuestions = listOf(
        Question(
            id = 1,
            text = "Ibukota negara Indonesia yang baru bernama apa?",
            options = listOf("Nusantara", "Jakarta", "Bandung", "Surabaya"),
            correctAnswerIndex = 0,
            difficulty = Difficulty.EASY
        )
    )
    QuizScreen(
        state = QuizUiState(
            questions = sampleQuestions,
            currentIndex = 0,
            score = 30,
            timeRemainingSeconds = 12,
            selectedOptionIndex = null,
            isAnswerLocked = false,
            difficulty = Difficulty.EASY
        ),
        onSelectOption = {}
    )
}
