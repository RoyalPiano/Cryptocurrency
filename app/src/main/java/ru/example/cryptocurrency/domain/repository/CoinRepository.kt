package ru.example.cryptocurrency.domain.repository

import ru.example.cryptocurrency.data.remote.dto.CoinDetailDto
import ru.example.cryptocurrency.data.remote.dto.CoinDto

interface CoinRepository {

    suspend fun getCoins(): List<CoinDto>

    suspend fun getCoinById(id: String): CoinDetailDto
}