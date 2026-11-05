package plat.rickandmorty.app.location.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import plat.rickandmorty.app.location.data.domain.Location
import plat.rickandmorty.app.location.data.local.LocationDAO
import plat.rickandmorty.app.location.data.local.mapToModel
import plat.rickandmorty.app.location.data.remote.LocationApi
import plat.rickandmorty.app.location.data.remote.mapToEntity
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor (
    private val api: LocationApi,
    private val dao: LocationDAO
) : LocationRepository {

    override suspend fun checkLoadLocations() {
        val localLocations = dao.getLocations()

        if (localLocations.first().isEmpty()) {
            val remoteLocations = api.getLocations()

            if (remoteLocations.isNotEmpty()) {
                dao.insertLocation(
                    *remoteLocations
                        .map { it.mapToEntity() }
                        .toTypedArray()
                )
            }
        }
    }

    override fun getLocations(): Flow<List<Location>> = flow {
        emitAll(
            dao.getLocations().map { entities ->
                entities.map { it.mapToModel() }
            }
        )
    }

    override fun getLocation(id: Int): Flow<Location> = flow {
        emitAll(
            dao.getLocationById(id).map { entity ->
                entity.mapToModel()
            }
        )
    }

    override suspend fun toggleLocationFavorite(id: Int) {
        dao.toggleLocationFavorite(id)
    }

    override suspend fun deleteLocations() {
        dao.deleteLocations()
    }
}