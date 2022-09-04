package ru.example.cryptocurrency.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.example.cryptocurrency.common.Resource
import ru.example.cryptocurrency.domain.model.Coin
import ru.example.cryptocurrency.domain.use_case.get_coins.GetCoinsUseCase
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<Resource<List<Coin>>>(Resource.Loading())
    val state: StateFlow<Resource<List<Coin>>> = _state

    private val _coinList = MutableStateFlow(emptyList<Coin>())
    val coinList: StateFlow<List<Coin>> = _coinList

    private var getCoinsJob: Job? = null

    fun refreshData() {
        getCoins()
    }

    init {
        getCoins()
    }

    private fun getCoins() {
        getCoinsJob?.cancel()
        getCoinsJob = getCoinsUseCase().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _coinList.emit(result.data)
                }
                is Resource.Error -> {
                    _coinList.emit(emptyList())
                }
                is Resource.Loading -> {}
            }
            _state.emit(result)
        }.launchIn(viewModelScope)
    }
}