package plat.rickandmorty.app.character.ui.list

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import plat.rickandmorty.app.character.data.CharacterRepository
import plat.rickandmorty.app.character.data.domain.Character
import plat.rickandmorty.app.core.data.AppState
import plat.rickandmorty.app.core.data.BaseViewModel
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

data class CharactersFilter(
    val onlyFavorites: Boolean = false
)

sealed interface CharactersEvent{
    data object FilterFavorites: CharactersEvent
    data class ToggleFavorite(
        val id: Int
    ): CharactersEvent
    data class Delete(
        val character: Character
    ): CharactersEvent
}

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : BaseViewModel<List<Character>, CharactersEvent>(
    loadingTime = 4.seconds
) {
    private val _filter = MutableStateFlow(CharactersFilter())
    val filter = _filter.asStateFlow()

    val filteredState: StateFlow<AppState<List<Character>>> =
        combine(state, _filter) { appState, filter ->
            appState.copy(
                data = appState.data?.filter { character ->
                    !filter.onlyFavorites || character.favorite
                }
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AppState()
        )

    init {
        triggerLoading()
    }

    override fun handleEvent(event: CharactersEvent) {
        when (event) {
            is CharactersEvent.ToggleFavorite -> toggleFavorite(event.id)
            is CharactersEvent.FilterFavorites -> filterFavorites()
            is CharactersEvent.Delete -> deleteCharacter(event.character)
        }
    }

    private fun toggleFavorite(id: Int) {
        viewModelScope.launch {
            characterRepository.toggleCharacterFavorite(id)
        }
    }

    private fun filterFavorites() {
        _filter.update { it.copy(onlyFavorites = !it.onlyFavorites) }
    }

    private fun deleteCharacter(character: Character) {
        viewModelScope.launch {
            characterRepository.deleteCharacter(character)
        }
    }

    override fun loadData(): Flow<List<Character>> {
        val characters = characterRepository.getCharacters()

        viewModelScope.launch {
            if (characters.first().isEmpty()) {
                triggerError()
            }
        }

        return characters
    }
}