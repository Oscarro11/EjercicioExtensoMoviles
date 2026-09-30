package plat.rickandmorty.app.character.ui.list

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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import coil3.compose.AsyncImage
import plat.rickandmorty.app.character.data.Character
import plat.rickandmorty.app.character.data.CharacterDb

@Composable
fun CharactersRoute(
    onCharacterClick: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    //data retrieval
    CharactersScreen(
        characters = CharacterDb.getAllCharacters(),
        onCharacterClick = onCharacterClick,
        modifier = modifier
    )
}

//These Composables cant be previewed, due to using an online Image
@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CharactersScreen(
    characters: List<Character>,
    onCharacterClick: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = { Text(
                text = "Characters",
                fontWeight = FontWeight.Bold
            ) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary
            )
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(characters) { character ->
                CharacterCard(
                    image = character.image,
                    name = character.name,
                    species = character.species,
                    status = character.status,
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
    image: String,
    name: String,
    species: String,
    status: String,
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
                model = image,
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
                text = name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 5.em
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = "$species - $status",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}