package plat.rickandmorty.app.location.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class LocationsResponseDTO(
    val results: List<LocationDTO>
)