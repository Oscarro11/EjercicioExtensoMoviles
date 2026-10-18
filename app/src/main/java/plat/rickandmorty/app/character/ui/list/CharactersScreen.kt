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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import plat.rickandmorty.app.R
import plat.rickandmorty.app.character.data.Character
import plat.rickandmorty.app.character.data.list.CharactersEvent
import plat.rickandmorty.app.character.data.list.CharactersFilter
import plat.rickandmorty.app.character.data.list.CharactersViewModel
import plat.rickandmorty.app.chores.ui.AppStateContent
import plat.rickandmorty.app.core.data.AppEvent
import plat.rickandmorty.app.core.data.AppState

@Composable
fun CharactersRoute(
    onCharacterClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CharactersViewModel = viewModel()
){
    val state by viewModel.filteredState.collectAsStateWithLifecycle()
    val filter by viewModel.filter.collectAsStateWithLifecycle()

    CharactersScreen(
        state = state,
        filter = filter,
        onCharacterClick = onCharacterClick,
        onViewModelChange = viewModel::onChange,
        onEvent = viewModel::onEvent,
        modifier = modifier
    )
}

@Composable
private fun CharactersScreen(
    state: AppState<List<Character>>,
    filter: CharactersFilter,
    onCharacterClick: (Int) -> Unit,
    onViewModelChange: (AppEvent) -> Unit,
    onEvent: (CharactersEvent) -> Unit,
    modifier: Modifier = Modifier
){
    AppStateContent(
        state = state,
        errorMessage = "Error al cargar la lista de personajes. Inténtelo de nuevo",
        onLoadingClick = { onViewModelChange(AppEvent.ClickLoading) },
        onRetryClick = { onViewModelChange(AppEvent.ClickRetry) },
        modifier = modifier
    ) { characters ->
        CharactersContent(
            characters = characters,
            onCharacterClick = onCharacterClick,
            filter = filter,
            onEvent = onEvent,
            modifier = modifier
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CharactersContent(
    characters: List<Character>,
    filter: CharactersFilter,
    onCharacterClick: (Int) -> Unit,
    onEvent: (CharactersEvent) -> Unit,
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

        FilterChip(
            selected = filter.onlyFavorites,
            onClick = { onEvent(CharactersEvent.FilterFavorites) },
            label = { Text("Favoritos") }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            if (characters.isEmpty() && filter.onlyFavorites) {
                item {
                    Text(
                        text = "No has seleccionado ninguno personaje favorito",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            else {
                items(characters) { character ->
                    CharacterCard(
                        character = character,
                        onFavorite = { onEvent(CharactersEvent.ToggleFavorite(character.id)) },
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
}

@Composable
private fun CharacterCard(
    character: Character,
    onFavorite: () -> Unit,
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
                fontSize = if (character.name.length > 20) 4.em else 5.em
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = "${character.species} - ${character.status}",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        IconButton(
            onClick = onFavorite
        ) {
            Icon(
                painter = painterResource(if (character.favorite) R.drawable.favorite_full else R.drawable.favorite_empty),
                contentDescription = null
            )
        }
    }
}