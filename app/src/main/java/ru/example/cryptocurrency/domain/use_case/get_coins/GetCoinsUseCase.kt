package ru.example.cryptocurrency.domain.use_case.get_coins

import kotlinx.coroutines.flow.Flow
import ru.example.cryptocurrency.common.Resource
import ru.example.cryptocurrency.domain.model.Coin
import ru.example.cryptocurrency.domain.repository.CoinRepository
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(): Flow<Resource<List<Coin>>> {
        return repository.getCoins()
    }
}