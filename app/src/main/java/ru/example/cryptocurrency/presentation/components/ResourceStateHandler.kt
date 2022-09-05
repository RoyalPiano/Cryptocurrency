package ru.example.cryptocurrency.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.example.cryptocurrency.R
import ru.example.cryptocurrency.common.Resource

@Composable
fun <T> ResourceStateHandler(state: Resource<T>, modifier: Modifier = Modifier, onRefreshClick: () -> Unit = {}) {
    Box(modifier = modifier) {
        when (state) {
            is Resource.Success -> {
            }
            is Resource.Error -> {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                ) {
                    Text(
                        text = state.message.asString(),
                        color = MaterialTheme.colors.error,
                        textAlign = TextAlign.Center,
                    )
                    TextButton(
                        onClick = {
                            onRefreshClick()
                        },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),

                        ) {
                        Text(text = stringResource(R.string.refresh_page_tip))
                    }
                }
            }
            is Resource.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}