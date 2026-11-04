package plat.rickandmorty.app.character.data

import kotlinx.coroutines.flow.Flow
import plat.rickandmorty.app.character.data.domain.Character

interface CharacterRepository {
    fun getCharacters(): Flow<List<Character>>
    fun getCharacter(id: Int): Flow<Character>
    suspend fun toggleCharacterFavorite(id: Int)
}