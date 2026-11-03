package plat.rickandmorty.app.location.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import plat.rickandmorty.app.core.Dependencies
import plat.rickandmorty.app.core.data.BaseViewModel
import plat.rickandmorty.app.location.data.LocationRepository
import plat.rickandmorty.app.location.data.domain.Location
import plat.rickandmorty.app.location.data.local.mapToModel
import plat.rickandmorty.app.location.navigation.LocationInfoDestination
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class LocationInfoViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val repository: LocationRepository
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