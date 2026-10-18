package plat.rickandmorty.app.location.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import plat.rickandmorty.app.chores.ui.AppStateContent
import plat.rickandmorty.app.core.data.AppEvent
import plat.rickandmorty.app.core.data.AppState
import plat.rickandmorty.app.location.data.Location
import plat.rickandmorty.app.location.data.list.LocationsViewModel

@Composable
fun LocationsRoute(
    onLocationClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LocationsViewModel = viewModel()
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    LocationsScreen(
        state = state,
        onLocationClick = onLocationClick,
        onViewModelChange = viewModel::onChange,
        modifier = modifier
    )
}

@Composable
private fun LocationsScreen(
    state: AppState<List<Location>>,
    onLocationClick: (Int) -> Unit,
    onViewModelChange: (AppEvent) -> Unit,
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
            modifier = modifier
        )
    }
}

@Composable
private fun LocationsContent(
    locations: List<Location>,
    onLocationClick: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    LazyColumn(
        contentPadding = PaddingValues(12.dp),
        modifier = modifier
    ) {
       items(
           items = locations
       ) { location ->
           LocationRow(
               name = location.name,
               type = location.type,
               modifier = Modifier
                   .padding(vertical = 16.dp)
                   .clickable(
                       onClick = { onLocationClick(location.id) }
                   )
           )
       }
    }
}

@Composable
private fun LocationRow(
    name: String,
    type: String,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = type,
            style = MaterialTheme.typography.bodySmall
        )
    }
}