package plat.rickandmorty.app.location.data.remote

import kotlinx.serialization.Serializable
import plat.rickandmorty.app.location.data.domain.Location
import plat.rickandmorty.app.location.data.local.LocationEntity

@Serializable
data class LocationDTO(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String
)

fun LocationDTO.mapToModel(): Location {
    return Location(
        id, name, type, dimension, false
    )
}

fun LocationDTO.mapToEntity(): LocationEntity {
    return LocationEntity(
        id, name, type, dimension, false
    )
}