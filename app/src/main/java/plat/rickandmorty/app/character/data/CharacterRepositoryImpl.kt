package plat.rickandmorty.app.character.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import plat.rickandmorty.app.character.data.domain.Character
import plat.rickandmorty.app.character.data.domain.mapToEntity
import plat.rickandmorty.app.character.data.local.CharacterDAO
import plat.rickandmorty.app.character.data.local.mapToModel
import plat.rickandmorty.app.character.data.remote.CharacterApi
import plat.rickandmorty.app.character.data.remote.mapToEntity
import plat.rickandmorty.app.core.data.ProfileImageStorage
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor (
    private val api: CharacterApi,
    private val dao: CharacterDAO,
    private val imageStorage: ProfileImageStorage
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

    override suspend fun updateCharacter(character: Character) {
        val stored = dao.getCharacterById(character.id).first()
        val oldImage = stored.image

        val finalImage = if (imageStorage.needsCopy(character.image)) {
            imageStorage.copyIn(character.image)
        } else {
            character.image
        }

        dao.updateCharacter(character.copy(image = finalImage).mapToEntity())

        if (oldImage != finalImage) {
            imageStorage.delete(oldImage)
        }
    }

    override suspend fun toggleCharacterFavorite(id: Int) {
        dao.toggleCharacterFavorite(id)
    }

    override suspend fun deleteCharacters() {
        val images = dao.getCharacters().first().map {it.image}
        dao.deleteCharacters()
        images.forEach { imageStorage.delete(it) }
    }

    override suspend fun deleteCharacter(character: Character) {
        dao.deleteCharacter(character.mapToEntity())
        imageStorage.delete(character.image)
    }
}