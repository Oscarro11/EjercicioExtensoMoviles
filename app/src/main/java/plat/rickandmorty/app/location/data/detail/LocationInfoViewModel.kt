package plat.rickandmorty.app.location.data.detail

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import plat.rickandmorty.app.core.data.BaseViewModel
import plat.rickandmorty.app.location.data.Location
import plat.rickandmorty.app.location.data.LocationDb
import plat.rickandmorty.app.location.navigation.LocationInfoDestination
import kotlin.time.Duration.Companion.seconds

class LocationInfoViewModel(
    val savedStateHandle: SavedStateHandle
) : BaseViewModel<Location>(
    loadingTime = 2.seconds
) {
    private val destination = savedStateHandle.toRoute<LocationInfoDestination>()

    init {
        triggerLoading()
    }

    override suspend fun loadData(): Location {
        return LocationDb.getLocationById(destination.id)
    }
}