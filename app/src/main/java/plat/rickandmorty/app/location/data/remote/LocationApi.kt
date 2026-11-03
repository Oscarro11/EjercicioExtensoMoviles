package plat.rickandmorty.app.location.data.remote

interface LocationApi {
    suspend fun getLocations(): List<LocationDTO>
}