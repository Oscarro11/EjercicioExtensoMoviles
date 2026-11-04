package plat.rickandmorty.app.character.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import plat.rickandmorty.app.character.data.CharacterRepository
import plat.rickandmorty.app.character.data.domain.Character
import plat.rickandmorty.app.character.navigation.CharacterInfoDestination
import plat.rickandmorty.app.core.data.BaseViewModel
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

sealed interface CharacterInfoEvent{
    data class ToggleFavorite(
        val id: Int
    ): CharacterInfoEvent
}

@HiltViewModel
class CharacterInfoViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val repository: CharacterRepository
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
            repository.toggleCharacterFavorite(id)
        }
    }

    override fun loadData(): Flow<Character> =
        repository.getCharacter(destination.id)
}