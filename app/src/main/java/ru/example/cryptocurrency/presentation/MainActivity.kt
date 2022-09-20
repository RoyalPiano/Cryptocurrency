package ru.example.cryptocurrency.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.example.cryptocurrency.R
import ru.example.cryptocurrency.common.Constants
import ru.example.cryptocurrency.presentation.coin_detail.CoinDetailScreen
import ru.example.cryptocurrency.presentation.coin_list.CoinListScreen
import ru.example.cryptocurrency.presentation.theme.CryptocurrencyTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.example.cryptocurrency.presentation.theme.DarkGray

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            CryptocurrencyTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(color = MaterialTheme.colors.background)

                ScaffoldCompose()
            }
        }
    }
}

@Composable
fun ScaffoldCompose() {
    Scaffold(
        topBar = { TopAppBarCompose() }
    ) {

        Box(Modifier.background(MaterialTheme.colors.background)) {
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

@Composable
fun TopAppBarCompose() {
    TopAppBar(
        title = { Text(text = stringResource(R.string.app_bar_titie), color = Color.White)},
        backgroundColor = DarkGray,
    )
}