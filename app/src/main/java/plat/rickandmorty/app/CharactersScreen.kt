package plat.rickandmorty.app

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import coil3.compose.AsyncImage

//These Composables cant be previewed, due to using an online Image
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CharactersScreen(onCharacterClick: (Int) -> Unit, modifier: Modifier = Modifier){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = { Text(
                text = "Characters",
                fontWeight = FontWeight.Bold
            ) },
            colors = TopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                scrolledContainerColor = Color.Unspecified,
                navigationIconContentColor = Color.Unspecified,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                actionIconContentColor = Color.Unspecified,
                subtitleContentColor = Color.Unspecified
            )
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(CharacterDb.getAllCharacters()) {character ->
                CharacterCard(
                    character = character,
                    modifier = modifier
                        .height(100.dp)
                        .clickable(
                            enabled = true,
                            onClick = {
                                onCharacterClick(character.id)
                            }
                        )
                )
            }
        }
    }
}

@Composable
private fun CharacterCard(
    character: Character,
    modifier: Modifier = Modifier
){
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(0.2f)
        ) {
            AsyncImage(
                model = character.image,
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .weight(0.8f)
                .padding(5.dp)
        ) {
            Text(
                text = character.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 5.em
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = "${character.species} - ${character.status}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}