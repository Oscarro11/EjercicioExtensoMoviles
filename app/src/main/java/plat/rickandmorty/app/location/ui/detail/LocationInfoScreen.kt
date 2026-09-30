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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import plat.rickandmorty.app.R
import plat.rickandmorty.app.core.InfoRow
import plat.rickandmorty.app.location.data.LocationDb

@Composable
fun LocationInfoRoute(
    locationId: Int,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
){
    val location = LocationDb.getLocationById(locationId)

    LocationInfoScreen(
        id = locationId,
        name = location.name,
        type = location.type,
        dimension = location.dimension,
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationInfoScreen(
    id: Int,
    name: String,
    type: String,
    dimension: String,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
){
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
                text = name,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(24.dp)
            )

            InfoRow(
                label = "ID",
                data = id.toString(),
                modifier = Modifier.padding(vertical = 4.dp)
            )

            InfoRow(
                label = "Type",
                data = type,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            InfoRow(
                label = "Dimensions",
                data = dimension,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun PreviewLocationInfoScreen(){
    val data = LocationDb.getLocationById(1)

    LocationInfoScreen(
        id = data.id,
        name = data.name,
        type = data.type,
        dimension = data.dimension,
        modifier = Modifier.fillMaxSize()
    )
}