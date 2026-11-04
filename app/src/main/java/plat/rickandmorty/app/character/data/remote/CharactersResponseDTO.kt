package plat.rickandmorty.app.character.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class CharactersResponseDTO (
    val results: List<CharacterDTO>
)