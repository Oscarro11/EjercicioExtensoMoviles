package plat.rickandmorty.app.location.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.plus
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import plat.rickandmorty.app.R
import plat.rickandmorty.app.chores.ui.AppStateContent
import plat.rickandmorty.app.core.components.InfoRow
import plat.rickandmorty.app.core.data.AppEvent
import plat.rickandmorty.app.core.data.AppState
import plat.rickandmorty.app.location.data.domain.Location

@Composable
fun LocationInfoRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LocationInfoViewModel = hiltViewModel()
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    LocationInfoScreen(
        state = state,
        onViewModelChange = viewModel::onChange,
        onEvent = viewModel::onEvent,
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@Composable
private fun LocationInfoScreen(
    state: AppState<Location>,
    onViewModelChange: (AppEvent) -> Unit,
    onEvent: (LocationInfoEvent) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
){
    AppStateContent(
        state = state,
        errorMessage = "Error al cargar información de la localización, Inténtelo de nuevo",
        onLoadingClick = { onViewModelChange(AppEvent.ClickLoading) },
        onRetryClick = { onViewModelChange(AppEvent.ClickRetry) },
        modifier = modifier
    ) { location ->
        LocationInfoContent(
            location = location,
            onBackClick = onBackClick,
            onEvent = onEvent,
            modifier = modifier
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun LocationInfoContent(
    location: Location,
    onBackClick: () -> Unit,
    onEvent: (LocationInfoEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Location Details",
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.arrow_back),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding + PaddingValues(24.dp))
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = location.name,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(24.dp)
                )

                IconButton(
                    onClick = { onEvent(LocationInfoEvent.ToggleFavorite(location.id)) }
                ) {
                    Icon(
                        painter = painterResource(if (!location.favorite) R.drawable.favorite_empty else R.drawable.favorite_full),
                        contentDescription = null
                    )
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(30.dp)
            ) {
                InfoRow(
                    label = "ID",
                    data = location.id.toString(),
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                InfoRow(
                    label = "Type",
                    data = location.type,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                InfoRow(
                    label = "Dimensions",
                    data = location.dimension,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

        }
    }
}