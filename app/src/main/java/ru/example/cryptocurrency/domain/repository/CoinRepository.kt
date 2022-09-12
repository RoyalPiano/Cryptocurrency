package ru.example.cryptocurrency.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.example.cryptocurrency.common.Resource
import ru.example.cryptocurrency.domain.model.Coin
import ru.example.cryptocurrency.domain.model.CoinDetail

interface CoinRepository {

    fun getCoins(): Flow<Resource<List<Coin>>>

    fun getCoinById(id: String): Flow<Resource<CoinDetail>>
}