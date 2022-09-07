package ru.example.cryptocurrency.presentation.coin_detail

import ru.example.cryptocurrency.common.UiText
import ru.example.cryptocurrency.domain.model.CoinDetail
import ru.example.cryptocurrency.presentation.interfaces.ScreenState

data class CoinDetailState(
    override val isLoading: Boolean = false,
    override val data: CoinDetail? = null,
    override val errorMessage: UiText? = null
): ScreenState<CoinDetail?>