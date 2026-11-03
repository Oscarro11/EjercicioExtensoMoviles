package plat.rickandmorty.app.location.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import plat.rickandmorty.app.location.data.domain.Location
import plat.rickandmorty.app.location.data.remote.LocationDTO

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

fun LocationEntity.mapToDTO(): LocationDTO {
    return LocationDTO(
        id, name, type, dimension
    )
}