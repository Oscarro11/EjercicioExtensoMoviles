package plat.rickandmorty.app.location.data.detail

import plat.rickandmorty.app.core.data.BaseViewModel
import plat.rickandmorty.app.location.data.Location
import plat.rickandmorty.app.location.data.LocationDb
import kotlin.time.Duration.Companion.seconds

class LocationInfoViewModel(
    val locationId: Int
) : BaseViewModel<Location>(
    loadingTime = 2.seconds
) {
    init {
        triggerLoading()
    }

    override suspend fun loadData(): Location {
        return LocationDb.getLocationById(locationId)
    }
}