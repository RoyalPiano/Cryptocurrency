package ru.example.cryptocurrency.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import ru.example.cryptocurrency.common.Resource
import ru.example.cryptocurrency.domain.use_case.get_coins.GetCoinsUseCase
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CoinListState())
    val state = _state.asStateFlow()

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
                    _state.emit(CoinListState(data = result.data, isLoading = false, errorMessage = null))
                }
                is Resource.Error -> {
                    _state.emit(CoinListState(errorMessage = result.message, isLoading = false, data = emptyList()))
                }
                is Resource.Loading -> {
                    _state.update { it.copy(isLoading = true, errorMessage = null) }
                }
            }
        }.launchIn(viewModelScope)
    }
}