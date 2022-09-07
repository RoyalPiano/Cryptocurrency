package ru.example.cryptocurrency.presentation.coin_list

import ru.example.cryptocurrency.common.UiText
import ru.example.cryptocurrency.domain.model.Coin
import ru.example.cryptocurrency.presentation.interfaces.ScreenState

data class CoinListState(
    override val isLoading: Boolean = false,
    override val data: List<Coin> = emptyList(),
    override val errorMessage: UiText? = null
): ScreenState<List<Coin>>