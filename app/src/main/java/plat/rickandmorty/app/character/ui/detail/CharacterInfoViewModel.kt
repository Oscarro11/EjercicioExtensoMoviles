package plat.rickandmorty.app.character.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import plat.rickandmorty.app.character.data.CharacterRepository
import plat.rickandmorty.app.character.data.domain.Character
import plat.rickandmorty.app.character.navigation.CharacterInfoDestination
import plat.rickandmorty.app.core.data.BaseViewModel
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

data class CharacterInfoState(
    val editionMode: Boolean = false,
    val draft: CharacterDraft? = null
)

data class CharacterDraft(
    val name: String,
    val image: String,
    val status: String,
    val species: String,
    val gender: String
) {
    val isValid get() = name.isNotBlank()
}

sealed interface CharacterInfoEvent{
    data class ToggleFavorite(val id: Int): CharacterInfoEvent
    data object StartEditionMode : CharacterInfoEvent
    data object EndEditionMode : CharacterInfoEvent
    data object CancelEditionMode : CharacterInfoEvent
    data class ChangeDraft(val draft: CharacterDraft): CharacterInfoEvent
}

@HiltViewModel
class CharacterInfoViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val repository: CharacterRepository
) : BaseViewModel<Character, CharacterInfoEvent>(
    loadingTime = 2.seconds
) {
    private val destination = savedStateHandle.toRoute<CharacterInfoDestination>()

    private val _infoState = MutableStateFlow(CharacterInfoState())
    val infoState = _infoState.asStateFlow()

    init {
        triggerLoading()
    }

    override fun handleEvent(event: CharacterInfoEvent) {
        when (event) {
            is CharacterInfoEvent.ToggleFavorite -> toggleFavorite(event.id)
            is CharacterInfoEvent.StartEditionMode -> startEditing()
            is CharacterInfoEvent.EndEditionMode -> saveEditing()
            is CharacterInfoEvent.CancelEditionMode -> cancelEditing()
            is CharacterInfoEvent.ChangeDraft -> changeDraft(event.draft)
        }
    }

    private fun toggleFavorite(id: Int){
        viewModelScope.launch {
            repository.toggleCharacterFavorite(id)
        }
    }

    private fun startEditing(){
        val character = state.value.data ?: return
        _infoState.update {
            it.copy(
                editionMode = true,
                draft = CharacterDraft(
                    name = character.name,
                    image = character.image,
                    status = character.status,
                    species = character.species,
                    gender = character.gender
                )
            )
        }
    }

    private fun saveEditing(){
        val original = state.value.data ?: return
        val draft = _infoState.value.draft ?: return
        if (!draft.isValid) return

        viewModelScope.launch {
            repository.updateCharacter(
                original.copy(
                    name = draft.name,
                    image = draft.image,
                    status = draft.status,
                    species = draft.species,
                    gender = draft.gender
                )
            )

            _infoState.update { it.copy(editionMode = false, draft = null) }
        }
    }

    private fun cancelEditing(){
        _infoState.update { it.copy(editionMode = false, draft = null) }
    }

    private fun changeDraft(draft: CharacterDraft){
        _infoState.update { it.copy(draft = draft) }
    }

    override fun loadData(): Flow<Character> =
        repository.getCharacter(destination.id)
}