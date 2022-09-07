package ru.example.cryptocurrency.presentation.interfaces

import ru.example.cryptocurrency.common.UiText

interface ScreenState<T> {
    val isLoading: Boolean
    val errorMessage: UiText?
    val data: T
}