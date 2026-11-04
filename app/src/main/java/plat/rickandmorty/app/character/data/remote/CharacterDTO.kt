package plat.rickandmorty.app.character.data.remote

import kotlinx.serialization.Serializable
import plat.rickandmorty.app.character.data.domain.Character
import plat.rickandmorty.app.character.data.local.CharacterEntity

@Serializable
data class CharacterDTO(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String,
)

fun CharacterDTO.mapToModel(): Character {
    return Character (
        id, name, status, species, gender, image, false
    )
}

fun CharacterDTO.mapToEntity(): CharacterEntity {
    return CharacterEntity(
        id, name, status, species, gender, image, false
    )
}