package plat.rickandmorty.app.character.data.detail

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import plat.rickandmorty.app.character.data.Character
import plat.rickandmorty.app.character.data.CharacterDb
import plat.rickandmorty.app.character.navigation.CharacterInfoDestination
import plat.rickandmorty.app.core.data.BaseViewModel
import kotlin.time.Duration.Companion.seconds

class CharacterInfoViewModel(
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<Character>(
    loadingTime = 2.seconds
) {
    private val destination = savedStateHandle.toRoute<CharacterInfoDestination>()

    init {
        triggerLoading()
    }

    override suspend fun loadData(): Character {
        return CharacterDb.getCharacterById(destination.id)
    }
}