package com.tamersarioglu.ratemecha.di


import com.tamersarioglu.ratemecha.data.repository.AuthRepositoryImpl
import com.tamersarioglu.ratemecha.data.repository.MechanicRepositoryImpl
import com.tamersarioglu.ratemecha.data.repository.ReviewRepositoryImpl
import com.tamersarioglu.ratemecha.domain.repository.AuthRepository
import com.tamersarioglu.ratemecha.domain.repository.MechanicRepository
import com.tamersarioglu.ratemecha.domain.repository.ReviewRepository
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
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindMechanicRepository(
        mechanicRepositoryImpl: MechanicRepositoryImpl
    ): MechanicRepository

    @Binds
    @Singleton
    abstract fun bindReviewRepository(
        reviewRepositoryImpl: ReviewRepositoryImpl
    ): ReviewRepository
}