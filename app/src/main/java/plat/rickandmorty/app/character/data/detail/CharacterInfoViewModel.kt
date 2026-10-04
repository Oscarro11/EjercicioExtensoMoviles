package plat.rickandmorty.app.character.data.detail

import plat.rickandmorty.app.character.data.Character
import plat.rickandmorty.app.character.data.CharacterDb
import plat.rickandmorty.app.core.data.BaseViewModel
import kotlin.time.Duration.Companion.seconds

class CharacterInfoViewModel(
    val characterId: Int
) : BaseViewModel<Character>(
    loadingTime = 2.seconds
) {
    init {
        triggerLoading()
    }

    override suspend fun loadData(): Character {
        return CharacterDb.getCharacterById(characterId)
    }
}