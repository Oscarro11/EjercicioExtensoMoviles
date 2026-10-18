package plat.rickandmorty.app.character.data.list

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import plat.rickandmorty.app.character.data.Character
import plat.rickandmorty.app.character.data.mapToModel
import plat.rickandmorty.app.core.Dependencies
import plat.rickandmorty.app.core.data.AppState
import plat.rickandmorty.app.core.data.BaseViewModel
import kotlin.time.Duration.Companion.seconds

data class CharactersFilter(
    val onlyFavorites: Boolean = false
)

sealed interface CharactersEvent{
    data object FilterFavorites: CharactersEvent
    data class ToggleFavorite(
        val id: Int
    ): CharactersEvent
}

class CharactersViewModel : BaseViewModel<List<Character>, CharactersEvent>(
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
        }
    }

    private fun toggleFavorite(id: Int) {
        viewModelScope.launch {
            Dependencies.database.characterDAO().toggleCharacterFavorite(id)
        }
    }

    private fun filterFavorites() {
        _filter.update { it.copy(onlyFavorites = !it.onlyFavorites) }
    }

    override fun loadData(): Flow<List<Character>> =
        Dependencies.database.characterDAO()
            .getCharacters()
            .map { entities -> entities.map { it.mapToModel() } }
}