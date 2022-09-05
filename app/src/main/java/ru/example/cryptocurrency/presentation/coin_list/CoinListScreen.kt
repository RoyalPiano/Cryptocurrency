package ru.example.cryptocurrency.presentation.coin_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.example.cryptocurrency.presentation.Screen
import ru.example.cryptocurrency.presentation.coin_list.components.CoinListItem
import ru.example.cryptocurrency.presentation.components.ResourceStateHandler

@Composable
fun CoinListScreen(
    navController: NavController,
    viewModel: CoinListViewModel = hiltViewModel()
) {
    val coinList = viewModel.coinList.collectAsState().value
    val state = viewModel.state.collectAsState().value

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
            ResourceStateHandler(state, Modifier.align(Center)) { viewModel.refreshData() }
        }
    }
}