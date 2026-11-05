package plat.rickandmorty.app.location.ui.list

import android.R.attr.type
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import plat.rickandmorty.app.R
import plat.rickandmorty.app.chores.ui.AppStateContent
import plat.rickandmorty.app.core.data.AppEvent
import plat.rickandmorty.app.core.data.AppState
import plat.rickandmorty.app.location.data.domain.Location

@Composable
fun LocationsRoute(
    onLocationClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LocationsViewModel = hiltViewModel()
){
    val state by viewModel.filteredState.collectAsStateWithLifecycle()
    val filter by viewModel.filter.collectAsStateWithLifecycle()

    LocationsScreen(
        state = state,
        filter = filter,
        onLocationClick = onLocationClick,
        onViewModelChange = viewModel::onChange,
        onEvent = viewModel::onEvent,
        modifier = modifier
    )
}

@Composable
private fun LocationsScreen(
    state: AppState<List<Location>>,
    filter: LocationsFilters,
    onLocationClick: (Int) -> Unit,
    onViewModelChange: (AppEvent) -> Unit,
    onEvent: (LocationsEvent) -> Unit,
    modifier: Modifier = Modifier
){
    AppStateContent(
        state = state,
        errorMessage = "Error al cargar la lista de localizaciones. Inténtelo de nuevo",
        onLoadingClick = { onViewModelChange(AppEvent.ClickLoading) },
        onRetryClick = { onViewModelChange(AppEvent.ClickRetry) },
        modifier = modifier
    ) { locations ->
        LocationsContent(
            locations = locations,
            onLocationClick = onLocationClick,
            filter = filter,
            onEvent = onEvent,
            modifier = modifier
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun LocationsContent(
    locations: List<Location>,
    filter: LocationsFilters,
    onLocationClick: (Int) -> Unit,
    onEvent: (LocationsEvent) -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = { Text(
                text = "Locations",
                fontWeight = FontWeight.Bold
            ) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary
            )
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilterChip(
                selected = filter.mode == LocationsFilterMode.TODAS,
                onClick = { onEvent(LocationsEvent.ChangeFilter(LocationsFilterMode.TODAS)) },
                label = { Text("Todas") }
            )

            FilterChip(
                selected = filter.mode == LocationsFilterMode.FAVORITAS,
                onClick = { onEvent(LocationsEvent.ChangeFilter(LocationsFilterMode.FAVORITAS)) },
                label = { Text("Favoritas") }
            )

            FilterChip(
                selected = filter.mode == LocationsFilterMode.NO_FAVORITAS,
                onClick = { onEvent(LocationsEvent.ChangeFilter(LocationsFilterMode.NO_FAVORITAS)) },
                label = { Text("No favoritas") }
            )
        }

        LazyColumn(
            contentPadding = PaddingValues(12.dp),
            modifier = modifier
        ) {
            if (locations.isEmpty()) {
                if (filter.mode == LocationsFilterMode.FAVORITAS) {
                    item {
                        Text(
                            text = "No has seleccionado una localización favorita",
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                } else if (filter.mode == LocationsFilterMode.NO_FAVORITAS) {
                    item {
                        Text(
                            text = "Todas tus localizaciones tienen favorito",
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
            else {
                items(locations) { location ->
                    LocationCard(
                        location = location,
                        onFavorite = { onEvent(LocationsEvent.ToggleFavorite(location.id)) },
                        modifier = Modifier
                            .clickable(
                                onClick = { onLocationClick(location.id) }
                            )
                    )
                }
            }

        }
    }
}

@Composable
private fun LocationCard(
    location: Location,
    onFavorite: () -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .weight(1f)
                .padding(5.dp)
        ) {
            Text(
                text = location.name,
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = location.type,
                style = MaterialTheme.typography.bodySmall
            )
        }

        IconButton(
            onClick = onFavorite
        ) {
            Icon(
                painter = painterResource(if (location.favorite) R.drawable.favorite_full else R.drawable.favorite_empty),
                contentDescription = null
            )
        }
    }
}