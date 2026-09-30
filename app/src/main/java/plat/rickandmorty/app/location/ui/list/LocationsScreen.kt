package plat.rickandmorty.app.location.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import plat.rickandmorty.app.location.data.Location
import plat.rickandmorty.app.location.data.LocationDb

@Composable
fun LocationsRoute(
    onLocationClick: (Int) -> Unit
){
    LocationsScreen(
        locations = LocationDb.getAllLocations(),
        onLocationClick = onLocationClick,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun LocationsScreen(
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

@Preview(showBackground = true)
@Composable
private fun PreviewLocationsScreen(){
    LocationsScreen(
        locations = LocationDb.getAllLocations(),
        onLocationClick = {},
        modifier = Modifier.fillMaxSize()
    )
}