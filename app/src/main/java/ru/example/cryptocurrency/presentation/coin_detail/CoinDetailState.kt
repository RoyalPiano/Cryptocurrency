package ru.example.cryptocurrency.presentation.coin_detail

import ru.example.cryptocurrency.domain.model.Coin
import ru.example.cryptocurrency.domain.model.CoinDetail

data class CoinDetailState(
    val isLoading: Boolean = false,
    val coin: CoinDetail? = null,
)