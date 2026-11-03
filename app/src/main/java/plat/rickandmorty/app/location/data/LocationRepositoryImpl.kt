package plat.rickandmorty.app.location.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import plat.rickandmorty.app.location.data.domain.Location
import plat.rickandmorty.app.location.data.domain.mapToEntity
import plat.rickandmorty.app.location.data.local.LocationDAO
import plat.rickandmorty.app.location.data.local.mapToModel
import plat.rickandmorty.app.location.data.remote.LocationApi
import plat.rickandmorty.app.location.data.remote.mapToModel
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor (
    private val api: LocationApi,
    private val dao: LocationDAO
) : LocationRepository {

    override suspend fun checkLoadLocations() {
        val cachedLocations = dao.getLocations().first()

        if (cachedLocations.isEmpty()) {
            api.getLocations().forEach { dto ->
                dao.insertLocation(
                    dto.mapToModel().mapToEntity()
                )
            }
        }
    }

    override fun getLocations(): Flow<List<Location>> = flow {
        checkLoadLocations()
        emitAll(
            dao.getLocations().map { entities ->
                entities.map { entity -> entity.mapToModel() }
            }
        )
    }

    override fun getLocation(id: Int): Flow<Location> = flow {
        checkLoadLocations()
        emitAll(
            dao.getLocationById(id).map { entity ->
                entity.mapToModel()
            }
        )
    }
}