package plat.rickandmorty.app.character.data.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import plat.rickandmorty.app.character.data.Character
import plat.rickandmorty.app.character.data.mapToModel
import plat.rickandmorty.app.character.navigation.CharacterInfoDestination
import plat.rickandmorty.app.core.Dependencies
import plat.rickandmorty.app.core.data.BaseViewModel
import kotlin.time.Duration.Companion.seconds

sealed interface CharacterInfoEvent{
    data class ToggleFavorite(
        val id: Int
    ): CharacterInfoEvent
}

class CharacterInfoViewModel(
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<Character, CharacterInfoEvent>(
    loadingTime = 2.seconds
) {
    private val destination = savedStateHandle.toRoute<CharacterInfoDestination>()

    init {
        triggerLoading()
    }

    override fun handleEvent(event: CharacterInfoEvent) {
        when (event) {
            is CharacterInfoEvent.ToggleFavorite -> toggleFavorite(event.id)
        }
    }

    private fun toggleFavorite(id: Int){
        viewModelScope.launch {
            Dependencies.database.characterDAO()
                .toggleCharacterFavorite(id)
        }
    }

    override fun loadData(): Flow<Character> =
        Dependencies.database.characterDAO()
            .getCharacterById(destination.id).map { characterEntity -> characterEntity.mapToModel() }
}