package com.example.kotlintemplate.di

import com.example.kotlintemplate.data.repository.QuestionRepository
import com.example.kotlintemplate.data.repository.ScoreRepository
import com.example.kotlintemplate.domain.repository.QuestionRepositoryImpl
import com.example.kotlintemplate.domain.repository.ScoreRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindQuestionRepository(
        questionRepositoryImpl: QuestionRepositoryImpl
    ): QuestionRepository

    @Binds
    @Singleton
    abstract fun bindScoreRepository(
        scoreRepositoryImpl: ScoreRepositoryImpl
    ): ScoreRepository
}