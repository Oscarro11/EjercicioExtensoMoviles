package plat.rickandmorty.app.location.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class LocationEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val type: String,
    val dimension: String
)

fun LocationEntity.mapToModel(): Location {
    return Location(
        id, name, type, dimension
    )
}