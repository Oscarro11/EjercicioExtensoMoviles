package plat.rickandmorty.app.character.ui.creation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import plat.rickandmorty.app.character.data.CharacterRepository
import javax.inject.Inject

data class CharacterCreationState(
    val finished: Boolean = false,
    val draft: CharacterDraft = CharacterDraft()
)

data class CharacterDraft(
    val name: String = "",
    val image: String? = null,
    val status: String = "Alive",
    val species: String = "",
    val gender: String = "Male"
) {
    val isValid get() = name.isNotBlank()
}

sealed interface CharacterCreationEvent{
    data class ChangeDraft(val draft: CharacterDraft): CharacterCreationEvent
    data object FinishCreation: CharacterCreationEvent
}

@HiltViewModel
class CharacterCreationViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel(){
    private val _state = MutableStateFlow(CharacterCreationState())
    val state = _state.asStateFlow()

    fun handleEvent(event: CharacterCreationEvent) {
        when (event) {
            is CharacterCreationEvent.ChangeDraft -> changeDraft(event.draft)
            is CharacterCreationEvent.FinishCreation -> finishCreation()
        }
    }

    private fun changeDraft(draft: CharacterDraft) {
        _state.update { it.copy(draft = draft) }
    }

    private fun finishCreation() {
        val draft = state.value.draft
        if (!draft.isValid) return

        viewModelScope.launch {
            repository.addCharacter(
                name = draft.name,
                image = draft.image,
                status = draft.status,
                species = draft.species,
                gender = draft.gender
            )
        }

        _state.update { it.copy(finished = true) }
    }
}