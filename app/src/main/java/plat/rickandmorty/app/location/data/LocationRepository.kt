package plat.rickandmorty.app.location.data

import kotlinx.coroutines.flow.Flow
import plat.rickandmorty.app.location.data.domain.Location

interface LocationRepository{
    fun getLocations(): Flow<List<Location>>
    fun getLocation(id: Int): Flow<Location>
}