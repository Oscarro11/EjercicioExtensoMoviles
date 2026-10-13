package plat.rickandmorty.app.location.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import plat.rickandmorty.app.R
import plat.rickandmorty.app.chores.ui.AppStateContent
import plat.rickandmorty.app.core.components.InfoRow
import plat.rickandmorty.app.core.data.AppState
import plat.rickandmorty.app.core.data.Event
import plat.rickandmorty.app.location.data.Location
import plat.rickandmorty.app.location.data.LocationDb
import plat.rickandmorty.app.location.data.detail.LocationInfoViewModel

@Composable
fun LocationInfoRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LocationInfoViewModel = viewModel()
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    LocationInfoScreen(
        state = state,
        onViewModelChange = viewModel::onChange,
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@Composable
private fun LocationInfoScreen(
    state: AppState<Location>,
    onViewModelChange: (Event) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
){
    AppStateContent(
        state = state,
        errorMessage = "Error al cargar información de la localización, Inténtelo de nuevo",
        onLoadingClick = { onViewModelChange(Event.ClickLoading) },
        onRetryClick = { onViewModelChange(Event.ClickRetry) },
        modifier = modifier
    ) { location ->
        LocationInfoContent(
            location = location,
            onBackClick = onBackClick,
            modifier = modifier
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun LocationInfoContent(
    location: Location,
    onBackClick: () -> Unit,
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
            Text(
                text = location.name,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(24.dp)
            )

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

@Preview(showBackground = true)
@Composable
private fun PreviewLocationInfoContent(){
    LocationInfoContent(
        location = LocationDb.getLocationById(1),
        onBackClick = {}
    )
}