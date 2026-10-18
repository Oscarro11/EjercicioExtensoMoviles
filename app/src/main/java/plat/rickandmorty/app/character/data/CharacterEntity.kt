package plat.rickandmorty.app.character.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterEntity (
    @PrimaryKey val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String,
    val favorite: Boolean
)

fun CharacterEntity.mapToModel(): Character {
    return Character(
        id, name, status, species, gender, image, favorite
    )
}