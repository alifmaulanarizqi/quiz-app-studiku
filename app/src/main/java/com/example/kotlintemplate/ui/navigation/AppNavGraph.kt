package com.example.kotlintemplate.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.kotlintemplate.ui.feature.difficulty.SelectDifficultyRoute
import com.example.kotlintemplate.ui.feature.home.HomeRoute
import com.example.kotlintemplate.ui.feature.quiz.QuizRoute
import com.example.kotlintemplate.ui.feature.result.ResultRoute

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String = Routes.HOME
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.HOME) {
            HomeRoute(
                onStartQuiz = {
                    navController.navigate(Routes.SELECT_DIFFICULTY)
                }
            )
        }

        composable(Routes.SELECT_DIFFICULTY) {
            SelectDifficultyRoute(
                onBackClick = {
                    navController.popBackStack()
                },
                onDifficultySelected = { difficulty ->
                    navController.navigate(Routes.quiz(difficulty.key))
                }
            )
        }

        composable(
            route = Routes.QUIZ,
            arguments = listOf(
                navArgument("difficulty") { type = NavType.StringType }
            )
        ) {
            QuizRoute(
                onQuizFinished = { difficulty, score, correctCount ->
                    navController.navigate(Routes.result(difficulty, score, correctCount)) {
                        popUpTo(Routes.SELECT_DIFFICULTY) { inclusive = false }
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Routes.RESULT,
            arguments = listOf(
                navArgument("difficulty") { type = NavType.StringType },
                navArgument("score") { type = NavType.IntType },
                navArgument("correctCount") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val difficulty = backStackEntry.arguments?.getString("difficulty").orEmpty()
            ResultRoute(
                onPlayAgain = {
                    if (difficulty.isNotEmpty()) {
                        navController.navigate(Routes.quiz(difficulty)) {
                            popUpTo(Routes.SELECT_DIFFICULTY) { inclusive = false }
                        }
                    } else {
                        navController.navigate(Routes.SELECT_DIFFICULTY) {
                            popUpTo(Routes.HOME) { inclusive = false }
                        }
                    }
                },
                onBackToHome = {
                    if (!navController.popBackStack(Routes.HOME, inclusive = false)) {
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.HOME) { inclusive = true }
                        }
                    }
                }
            )
        }
    }
}