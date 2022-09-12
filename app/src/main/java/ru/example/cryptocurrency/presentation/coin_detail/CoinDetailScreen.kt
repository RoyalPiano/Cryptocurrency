package ru.example.cryptocurrency.presentation.coin_detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.example.cryptocurrency.R
import ru.example.cryptocurrency.presentation.coin_detail.components.CoinTag
import ru.example.cryptocurrency.presentation.coin_detail.components.TeamListItem
import ru.example.cryptocurrency.presentation.components.ScreenStateHandler
import ru.example.cryptocurrency.presentation.theme.spacing

@Composable
fun CoinDetailScreen(
    viewModel: CoinDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    SwipeRefresh(
        state = rememberSwipeRefreshState(false),
        onRefresh = {
            viewModel.refreshData()
        }) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(MaterialTheme.spacing.medium)
            ) {
                state.data?.let { coinDetail ->
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${coinDetail.rank}. ${coinDetail.name} (${coinDetail.symbol})",
                                style = MaterialTheme.typography.h5,
                            )
                            Text(
                                text = if(coinDetail.is_active)
                                    stringResource(R.string.active_indicator)
                                else
                                    stringResource(R.string.inactive_indicator),
                                color = if(coinDetail.is_active) Color.Green else Color.Red,
                                fontStyle = FontStyle.Italic,
                                textAlign = TextAlign.End,
                                modifier = Modifier
                                    .align(CenterVertically)
                            )
                        }
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                        Text(
                            text = coinDetail.description,
                            style = MaterialTheme.typography.body2
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                        Text(
                            text = stringResource(R.string.tag_title),
                            style = MaterialTheme.typography.h6
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                        FlowRow(
                            mainAxisSpacing = MaterialTheme.spacing.small,
                            crossAxisSpacing = MaterialTheme.spacing.small,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            coinDetail.tags.forEach { tag ->
                                CoinTag(tag = tag)
                            }
                        }
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                        Text(
                            text = stringResource(R.string.team_members_title),
                            style = MaterialTheme.typography.h6
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                    }
                    items(coinDetail.team) { teamMember ->
                        TeamListItem(
                            teamMember = teamMember,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(MaterialTheme.spacing.small)
                        )
                        Divider()
                    }
                }
            }
            ScreenStateHandler(state, Modifier.align(Alignment.Center)) { viewModel.refreshData() }
        }
    }
}