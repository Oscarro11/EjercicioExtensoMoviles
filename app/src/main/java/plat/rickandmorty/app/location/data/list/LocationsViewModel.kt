package plat.rickandmorty.app.location.data.list

import plat.rickandmorty.app.core.data.BaseViewModel
import plat.rickandmorty.app.location.data.Location
import plat.rickandmorty.app.location.data.LocationDb
import kotlin.time.Duration.Companion.seconds

class LocationsViewModel : BaseViewModel<List<Location>> (
    loadingTime = 4.seconds
) {
    init {
        triggerLoading()
    }

    override suspend fun loadData(): List<Location> {
        return LocationDb.getAllLocations()
    }
}