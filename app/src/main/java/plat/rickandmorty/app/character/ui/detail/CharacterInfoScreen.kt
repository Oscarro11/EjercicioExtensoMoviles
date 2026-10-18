package plat.rickandmorty.app.character.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.FixedScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import plat.rickandmorty.app.R
import plat.rickandmorty.app.character.data.Character
import plat.rickandmorty.app.character.data.detail.CharacterInfoEvent
import plat.rickandmorty.app.character.data.detail.CharacterInfoViewModel
import plat.rickandmorty.app.chores.ui.AppStateContent
import plat.rickandmorty.app.core.components.InfoRow
import plat.rickandmorty.app.core.data.AppEvent
import plat.rickandmorty.app.core.data.AppState

@Composable
fun CharacterInfoRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CharacterInfoViewModel = viewModel()
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    CharacterInfoScreen(
        state = state,
        onViewModelChange = viewModel::onChange,
        onEvent = viewModel::onEvent,
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@Composable
private fun CharacterInfoScreen(
    state: AppState<Character>,
    onViewModelChange: (AppEvent) -> Unit,
    onEvent: (CharacterInfoEvent) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
){
    AppStateContent(
        state = state,
        errorMessage = "Error al cargar información del personaje, Inténtelo de nuevo",
        onLoadingClick = { onViewModelChange(AppEvent.ClickLoading) },
        onRetryClick = { onViewModelChange(AppEvent.ClickRetry) },
        modifier = modifier
    ) { character ->
        CharacterInfoContent(
            character = character,
            onBackClick = onBackClick,
            onEvent = onEvent,
            modifier = modifier
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CharacterInfoContent(
    character: Character,
    onBackClick: () -> Unit,
    onEvent: (CharacterInfoEvent) -> Unit,
    modifier: Modifier = Modifier
){
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
                        painter = painterResource(id = R.drawable.arrow_back),
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

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = character.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 5.em,
                modifier = Modifier
                    .padding(10.dp)
            )

            IconButton(
                onClick = { onEvent(CharacterInfoEvent.ToggleFavorite(character.id)) }
            ) {
                Icon(
                    painter = painterResource(if (!character.favorite) R.drawable.favorite_empty else R.drawable.favorite_full),
                    contentDescription = null
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(30.dp)
        ) {
            InfoRow(label = "Species", data = character.species)
            InfoRow(label = "Status", data = character.status)
            InfoRow(label = "Gender", data = character.gender)
        }
    }
}