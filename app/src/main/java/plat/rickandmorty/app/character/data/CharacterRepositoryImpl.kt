package plat.rickandmorty.app.character.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import plat.rickandmorty.app.character.data.domain.Character
import plat.rickandmorty.app.character.data.local.CharacterDAO
import plat.rickandmorty.app.character.data.local.mapToModel
import plat.rickandmorty.app.character.data.remote.CharacterApi
import plat.rickandmorty.app.character.data.remote.mapToEntity
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor (
    private val api: CharacterApi,
    private val dao: CharacterDAO
) : CharacterRepository {

    override suspend fun checkLoadCharacters() {
        val localCharacters = dao.getCharacters()

        if (localCharacters.first().isEmpty()) {
            val remoteCharacters = api.getCharacters()

            if (remoteCharacters.isNotEmpty()) {
                dao.insertCharacter(
                    *remoteCharacters
                        .map { it.mapToEntity() }
                        .toTypedArray()
                )
            }
        }
    }

    override fun getCharacters(): Flow<List<Character>> = flow {
        emitAll(
            dao.getCharacters().map { entities ->
                entities.map { it.mapToModel() }
            }
        )
    }

    override fun getCharacter(id: Int): Flow<Character> = flow {
        emitAll(
            dao.getCharacterById(id).map { entity ->
                entity.mapToModel()
            }
        )
    }

    override suspend fun toggleCharacterFavorite(id: Int) {
        dao.toggleCharacterFavorite(id)
    }

    override suspend fun deleteCharacters() {
        dao.deleteCharacters()
    }
}