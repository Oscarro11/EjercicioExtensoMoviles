package plat.rickandmorty.app.character.data.list

import plat.rickandmorty.app.character.data.Character
import plat.rickandmorty.app.character.data.CharacterDb
import plat.rickandmorty.app.core.data.BaseViewModel
import kotlin.time.Duration.Companion.seconds

class CharactersViewModel : BaseViewModel<List<Character>>(
    loadingTime = 4.seconds
) {
    init {
        triggerLoading()
    }

    override suspend fun loadData(): List<Character> {
        return CharacterDb.getAllCharacters()
    }
}