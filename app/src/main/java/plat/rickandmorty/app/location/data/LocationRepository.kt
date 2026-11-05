package plat.rickandmorty.app.location.data

import kotlinx.coroutines.flow.Flow
import plat.rickandmorty.app.location.data.domain.Location

interface LocationRepository{
    suspend fun checkLoadLocations()
    fun getLocations(): Flow<List<Location>>
    fun getLocation(id: Int): Flow<Location>
    suspend fun toggleLocationFavorite(id: Int)
    suspend fun deleteLocations()
}