package plat.rickandmorty.app.character.data

import kotlinx.coroutines.flow.Flow
import plat.rickandmorty.app.character.data.domain.Character

interface CharacterRepository {
    suspend fun checkLoadCharacters()
    fun getCharacters(): Flow<List<Character>>
    fun getCharacter(id: Int): Flow<Character>
    suspend fun toggleCharacterFavorite(id: Int)
    suspend fun deleteCharacters()
    suspend fun deleteCharacter(character: Character)
}