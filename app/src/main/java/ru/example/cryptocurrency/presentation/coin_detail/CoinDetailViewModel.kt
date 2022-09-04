package ru.example.cryptocurrency.presentation.coin_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.example.cryptocurrency.common.Constants
import ru.example.cryptocurrency.common.Resource
import ru.example.cryptocurrency.domain.model.CoinDetail
import ru.example.cryptocurrency.domain.use_case.get_coin.GetCoinUseCase
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val getCoinUseCase: GetCoinUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow<Resource<CoinDetail>>(Resource.Loading())
    val state: StateFlow<Resource<CoinDetail>> = _state

    private val _coin = MutableStateFlow<CoinDetail?>(null)
    val coin: StateFlow<CoinDetail?> = _coin

    private var getCoinJob: Job? = null
    private var coinId: String? = savedStateHandle.get<String>(Constants.PARAM_COIN_ID)

    init {
        coinId?.let { coinId ->
            getCoin(coinId)
        }
    }

    fun refreshData() {
        coinId?.let { getCoin(it) }
    }

    private fun getCoin(coinId: String) {
        getCoinJob?.cancel()
        getCoinJob = getCoinUseCase(coinId).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _coin.emit(result.data)
                }
                is Resource.Error -> {
                    _coin.emit(null)
                }
                is Resource.Loading -> {}
            }
            _state.emit(result)
        }.launchIn(viewModelScope)
    }
}