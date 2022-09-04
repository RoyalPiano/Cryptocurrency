package ru.example.cryptocurrency.domain.use_case.get_coins

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ru.example.cryptocurrency.R
import ru.example.cryptocurrency.common.Resource
import ru.example.cryptocurrency.common.UiText
import ru.example.cryptocurrency.data.remote.dto.toCoin
import ru.example.cryptocurrency.domain.model.Coin
import ru.example.cryptocurrency.domain.repository.CoinRepository
import java.io.IOException
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(): Flow<Resource<List<Coin>>> = flow {
        try {
            emit(Resource.Loading())
            val coins = repository.getCoins().map { it.toCoin() }
            emit(Resource.Success(coins))
        } catch(e: HttpException) {
            if(e.localizedMessage.isNullOrEmpty()) {
                emit(Resource.Error(UiText.StringResource(R.string.unknown_exception)))
            }
            else {
                emit(Resource.Error(UiText.DynamicString(e.localizedMessage!!)))
            }
        } catch(e: IOException) {
            emit(Resource.Error(UiText.StringResource(R.string.io_exception)))
        }
    }
}