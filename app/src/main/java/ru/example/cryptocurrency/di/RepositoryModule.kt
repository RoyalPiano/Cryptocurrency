package ru.example.cryptocurrency.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.example.cryptocurrency.data.repository.CoinRepositoryImpl
import ru.example.cryptocurrency.domain.repository.CoinRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: CoinRepositoryImpl
    ): CoinRepository
}