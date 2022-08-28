package ru.example.cryptocurrency

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.example.cryptocurrency.common.Constants
import ru.example.cryptocurrency.presentation.Screen
import ru.example.cryptocurrency.presentation.coin_detail.CoinDetailScreen
import ru.example.cryptocurrency.presentation.coin_list.CoinListScreen
import ru.example.cryptocurrency.presentation.theme.CryptocurrencyTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScaffoldCompose()
        }
    }
}

@Composable
fun ScaffoldCompose() {
    Scaffold(
        topBar = { TopAppBarCompose() }
    ) {
        CryptocurrencyTheme {
            Surface(color = MaterialTheme.colors.background) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.CoinListScreen.route
                ) {
                    composable(
                        route = Screen.CoinListScreen.route
                    ) {
                        CoinListScreen(navController)
                    }
                    composable(
                        route = "${Screen.CoinDetailScreen.route}/{${Constants.PARAM_COIN_ID}}",
                    ) {
                        CoinDetailScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun TopAppBarCompose() {
    TopAppBar(
        title = { Text(text = "Crypto", color = Color.White)},
        backgroundColor = Color.DarkGray,
    )
}