package plat.rickandmorty.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.FixedScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import coil3.compose.AsyncImage

//This Composable cant be previewed, due to using an online Image
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CharacterInfoScreen(character: Character, onBackClick: () -> Unit, modifier: Modifier = Modifier){

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            navigationIcon = {
                IconButton(
                    onClick = onBackClick
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                        contentDescription = null
                    )
                }
            },
            title = {
                Text(
                    text = "Character details"
                )
            }
        )

        AsyncImage(
            model = character.image,
            contentDescription = null,
            contentScale = FixedScale(2f),
            modifier = Modifier
                .clip(CircleShape)
        )

        Text(
            text = character.name,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 5.em,
            modifier = Modifier
                .padding(10.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(30.dp)
        ) {
            ShortInfo(category = "Species", info = character.species)
            ShortInfo(category = "Status", info = character.status)
            ShortInfo(category = "Gender", info = character.gender)
        }
    }
}

@Composable
fun ShortInfo(category: String, info: String, modifier: Modifier = Modifier){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "$category:",
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = info,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}