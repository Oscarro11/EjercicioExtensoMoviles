package plat.rickandmorty.app.character.data

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String,
    val favorite: Boolean
)

fun Character.mapToEntity(): CharacterEntity {
    return CharacterEntity(
        id, name, status, species, gender, image, favorite
    )
}