package ru.example.cryptocurrency.domain.use_case.get_coin

import kotlinx.coroutines.flow.Flow
import ru.example.cryptocurrency.common.Resource
import ru.example.cryptocurrency.domain.model.CoinDetail
import ru.example.cryptocurrency.domain.repository.CoinRepository
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(coinId: String): Flow<Resource<CoinDetail>> {
        return repository.getCoinById(coinId)
    }
}