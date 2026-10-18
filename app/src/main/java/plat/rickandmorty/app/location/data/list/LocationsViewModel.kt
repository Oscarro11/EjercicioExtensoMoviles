package plat.rickandmorty.app.location.data.list

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import plat.rickandmorty.app.core.Dependencies
import plat.rickandmorty.app.core.data.BaseViewModel
import plat.rickandmorty.app.location.data.Location
import plat.rickandmorty.app.location.data.mapToModel
import kotlin.time.Duration.Companion.seconds

class LocationsViewModel : BaseViewModel<List<Location>, Nothing> (
    loadingTime = 4.seconds
) {
    init {
        triggerLoading()
    }

    override fun handleEvent(event: Nothing) {}

    override fun loadData(): Flow<List<Location>> =
        Dependencies.database.locationDAO()
            .getLocations().map { locationEntities ->
                locationEntities.map { locationEntity ->
                    locationEntity.mapToModel()
                }
            }
}