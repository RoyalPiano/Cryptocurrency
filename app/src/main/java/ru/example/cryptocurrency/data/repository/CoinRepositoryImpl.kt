package ru.example.cryptocurrency.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ru.example.cryptocurrency.R
import ru.example.cryptocurrency.common.Resource
import ru.example.cryptocurrency.common.UiText
import ru.example.cryptocurrency.data.remote.CoinPaprikaApi
import ru.example.cryptocurrency.data.remote.dto.CoinDetailDto
import ru.example.cryptocurrency.data.remote.dto.CoinDto
import ru.example.cryptocurrency.data.remote.dto.toCoin
import ru.example.cryptocurrency.data.remote.dto.toCoinDetail
import ru.example.cryptocurrency.domain.model.Coin
import ru.example.cryptocurrency.domain.model.CoinDetail
import ru.example.cryptocurrency.domain.repository.CoinRepository
import java.io.IOException
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api: CoinPaprikaApi
): CoinRepository {

    override fun getCoins(): Flow<Resource<List<Coin>>> {
        return flow {
            try {
                emit(Resource.Loading())
                val coins = api.getCoins().map { it.toCoin() }
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

    override fun getCoinById(id: String): Flow<Resource<CoinDetail>> {
        return flow {
            try {
                emit(Resource.Loading())
                val coin = api.getCoinById(id).toCoinDetail()
                emit(Resource.Success(coin))
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
}