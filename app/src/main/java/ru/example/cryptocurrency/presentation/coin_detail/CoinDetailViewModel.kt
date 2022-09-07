package ru.example.cryptocurrency.presentation.coin_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import ru.example.cryptocurrency.common.Constants
import ru.example.cryptocurrency.common.Resource
import ru.example.cryptocurrency.domain.use_case.get_coin.GetCoinUseCase
import ru.example.cryptocurrency.presentation.coin_list.CoinListState
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val getCoinUseCase: GetCoinUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(CoinDetailState())
    val state = _state.asStateFlow()

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
                    _state.emit(CoinDetailState(isLoading = false, data = result.data, errorMessage = null))
                }
                is Resource.Error -> {
                    _state.emit(CoinDetailState(errorMessage = result.message, isLoading = false, data = null))
                }
                is Resource.Loading -> {
                    _state.update { it.copy(isLoading = true, errorMessage = null)  }
                }
            }
        }.launchIn(viewModelScope)
    }
}