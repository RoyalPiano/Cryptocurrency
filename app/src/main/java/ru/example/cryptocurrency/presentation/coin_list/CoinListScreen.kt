package ru.example.cryptocurrency.presentation.coin_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.example.cryptocurrency.R
import ru.example.cryptocurrency.common.Resource
import ru.example.cryptocurrency.presentation.Screen
import ru.example.cryptocurrency.presentation.coin_list.components.CoinListItem

@Composable
fun CoinListScreen(
    navController: NavController,
    viewModel: CoinListViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val coinList = viewModel.coinList.collectAsState().value

    SwipeRefresh(
        state = rememberSwipeRefreshState(false),
        onRefresh = {
            viewModel.refreshData()
        }) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(coinList) { coin ->
                    CoinListItem(
                        coin = coin,
                        onItemClick = {
                            navController.navigate("${Screen.CoinDetailScreen.route}/${coin.id}")
                        }
                    )
                }
            }
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
                                viewModel.refreshData()
                            },
                            modifier = Modifier
                                .align(CenterHorizontally),

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
}