package plat.rickandmorty.app.character.ui.creation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import plat.rickandmorty.app.R
import plat.rickandmorty.app.core.components.OptionDropdown
import plat.rickandmorty.app.core.components.ProfileImageWithPhotoSelector

@Composable
fun CharacterCreationRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CharacterCreationViewModel = hiltViewModel()
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.finished) {
        if (state.finished) onBackClick()
    }

    CharacterCreationScreen(
        state = state,
        onEvent = viewModel::handleEvent,
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CharacterCreationScreen(
    state: CharacterCreationState,
    onEvent: (CharacterCreationEvent) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
){
    val draft = state.draft

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
                    text = "Character creation"
                )
            }
        )

        ProfileImageWithPhotoSelector(
            model = draft.image,
            onModelChange = { onEvent(CharacterCreationEvent.ChangeDraft(draft.copy(image = it)))},
            modifier = Modifier.size(200.dp)
        )

        OutlinedTextField(
            value = draft.name,
            onValueChange = { value ->
                onEvent(CharacterCreationEvent.ChangeDraft(draft.copy(name = value)))
            },
            label = { Text("Name: ") }
        )

        CharacterWriteFields(
            draft = draft,
            onDraftChange = { updatedDraft ->
                onEvent(CharacterCreationEvent.ChangeDraft(updatedDraft))
            }
        )

        Button(
            onClick = { onEvent(CharacterCreationEvent.FinishCreation) }
        ) {
            Text("Crear personaje")
        }
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