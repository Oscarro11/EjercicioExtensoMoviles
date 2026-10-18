package plat.rickandmorty.app.location.data.detail

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import plat.rickandmorty.app.core.Dependencies
import plat.rickandmorty.app.core.data.BaseViewModel
import plat.rickandmorty.app.location.data.Location
import plat.rickandmorty.app.location.data.mapToModel
import plat.rickandmorty.app.location.navigation.LocationInfoDestination
import kotlin.time.Duration.Companion.seconds

class LocationInfoViewModel(
    val savedStateHandle: SavedStateHandle
) : BaseViewModel<Location, Nothing>(
    loadingTime = 2.seconds
) {
    private val destination = savedStateHandle.toRoute<LocationInfoDestination>()

    init {
        triggerLoading()
    }

    override fun handleEvent(event: Nothing) {}

    override fun loadData(): Flow<Location> =
        Dependencies.database.locationDAO()
            .getLocationById(destination.id).map { locationEntity -> locationEntity.mapToModel() }
}