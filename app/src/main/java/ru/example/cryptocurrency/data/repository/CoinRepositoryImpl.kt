package ru.example.cryptocurrency.data.repository

import ru.example.cryptocurrency.data.remote.CoinPaprikaApi
import ru.example.cryptocurrency.data.remote.dto.CoinDetailDto
import ru.example.cryptocurrency.data.remote.dto.CoinDto
import ru.example.cryptocurrency.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api: CoinPaprikaApi
): CoinRepository {

    override suspend fun getCoins(): List<CoinDto> {
        return api.getCoins()
    }

    override suspend fun getCoinById(id: String): CoinDetailDto {
        return api.getCoinById(id)
    }
}