package ru.example.cryptocurrency.presentation.coin_list

import ru.example.cryptocurrency.domain.model.Coin

data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<Coin> = emptyList(),
)