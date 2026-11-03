package plat.rickandmorty.app.location.data.domain

import plat.rickandmorty.app.location.data.local.LocationEntity
import plat.rickandmorty.app.location.data.remote.LocationDTO

data class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String
)

fun Location.mapToEntity(): LocationEntity {
    return LocationEntity(
        id, name, type, dimension
    )
}

fun Location.mapToDTO(): LocationDTO {
    return LocationDTO(
        id, name, type, dimension
    )
}