package plat.rickandmorty.app.character.ui.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import plat.rickandmorty.app.R
import plat.rickandmorty.app.character.data.domain.Character
import plat.rickandmorty.app.chores.ui.AppStateContent
import plat.rickandmorty.app.core.components.InfoReadRow
import plat.rickandmorty.app.core.components.OptionDropdown
import plat.rickandmorty.app.core.components.ProfileImage
import plat.rickandmorty.app.core.components.ProfileImageWithPhotoSelector
import plat.rickandmorty.app.core.data.AppEvent
import plat.rickandmorty.app.core.data.AppState

@Composable
fun CharacterInfoRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CharacterInfoViewModel = hiltViewModel()
){
    val state by viewModel.state.collectAsStateWithLifecycle()
    val infoState by viewModel.infoState.collectAsStateWithLifecycle()

    CharacterInfoScreen(
        state = state,
        infoState = infoState,
        onViewModelChange = viewModel::onChange,
        onEvent = viewModel::onEvent,
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@Composable
private fun CharacterInfoScreen(
    state: AppState<Character>,
    infoState: CharacterInfoState,
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
            infoState = infoState,
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
    infoState: CharacterInfoState,
    onBackClick: () -> Unit,
    onEvent: (CharacterInfoEvent) -> Unit,
    modifier: Modifier = Modifier
){
    val draft = infoState.draft

    BackHandler(enabled = infoState.editionMode) {
        onEvent(CharacterInfoEvent.CancelEditionMode)
    }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            navigationIcon = {
                if (infoState.editionMode) {
                    IconButton(
                        onClick = {
                            onEvent(CharacterInfoEvent.CancelEditionMode)
                            onBackClick()
                        }
                    ) {
                        Icon(
                            painterResource(id = R.drawable.arrow_back),
                            contentDescription = null
                        )
                    }
                } else {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = null
                        )
                    }
                }

            },
            title = {
                Text(
                    text = "Character details"
                )
            },
            actions = {
                if (infoState.editionMode) {
                    IconButton(
                        onClick = { onEvent(CharacterInfoEvent.EndEditionMode) },
                        enabled = draft?.isValid == true
                    ) {
                        Icon(
                            painterResource(R.drawable.check),
                            contentDescription = "Guardar"
                        )
                    }
                } else {
                    IconButton(
                        onClick = { onEvent(CharacterInfoEvent.StartEditionMode) }
                    ) {
                        Icon(
                            painterResource(R.drawable.edit),
                            contentDescription = "Editar"
                        )
                    }
                }
            }
        )

        if (infoState.editionMode && draft != null) {
            ProfileImageWithPhotoSelector(
                model = draft.image,
                onModelChange = { onEvent(CharacterInfoEvent.ChangeDraft(draft.copy(image = it)))},
                modifier = Modifier.size(200.dp)
            )
        } else {
            ProfileImage(
                image = character.image,
                modifier = Modifier.size(200.dp)
            )
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (infoState.editionMode && draft != null) {
                OutlinedTextField(
                    label = { Text("Name: ") },
                    value = draft.name,
                    onValueChange = { value ->
                        onEvent(CharacterInfoEvent.ChangeDraft(draft.copy(name = value)))
                    }
                )
            } else {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 5.em,
                    modifier = Modifier
                        .padding(10.dp)
                )
            }

            IconButton(
                onClick = { onEvent(CharacterInfoEvent.ToggleFavorite(character.id)) }
            ) {
                Icon(
                    painter = painterResource(if (!character.favorite) R.drawable.favorite_empty else R.drawable.favorite_full),
                    contentDescription = null
                )
            }
        }

        if (infoState.editionMode && draft != null){
            CharacterWriteFields(
                draft = draft,
                onDraftChange = { updatedDraft ->
                    onEvent(CharacterInfoEvent.ChangeDraft(updatedDraft))
                }
            )
        } else {
            CharacterReadFields(character = character)
        }
    }
}

@Composable
private fun CharacterReadFields(
    character: Character
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(30.dp)
    ) {
        InfoReadRow(label = "Species", data = character.species)
        InfoReadRow(label = "Status", data = character.status)
        InfoReadRow(label = "Gender", data = character.gender)
    }
}

@Composable
private fun CharacterWriteFields(
    draft: CharacterDraft,
    onDraftChange: (CharacterDraft) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(30.dp)
    ) {
        OutlinedTextField(
            label = { Text("Species: ") },
            value = draft.species,
            onValueChange = { species ->
                onDraftChange(draft.copy(species = species))
            },
            modifier = Modifier.fillMaxWidth()
        )

        OptionDropdown(
            label = "Status",
            selected = draft.status,
            options = listOf("Alive", "Dead", "Unknown"),
            onSelect = { onDraftChange(draft.copy(status = it)) }
        )

        OptionDropdown(
            label = "Gender",
            selected = draft.gender,
            options = listOf("Female", "Male", "Genderless", "unknown"),
            onSelect = { onDraftChange(draft.copy(gender = it)) }
        )
    }
}