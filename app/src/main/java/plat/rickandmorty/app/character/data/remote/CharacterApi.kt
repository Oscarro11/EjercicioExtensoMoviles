package plat.rickandmorty.app.character.data.remote

interface CharacterApi {
    suspend fun getCharacters(): List<CharacterDTO>
}