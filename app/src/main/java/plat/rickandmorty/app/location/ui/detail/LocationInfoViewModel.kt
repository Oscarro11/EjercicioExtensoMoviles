package plat.rickandmorty.app.location.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import plat.rickandmorty.app.core.data.BaseViewModel
import plat.rickandmorty.app.location.data.LocationRepository
import plat.rickandmorty.app.location.data.domain.Location
import plat.rickandmorty.app.location.navigation.LocationInfoDestination
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

sealed interface LocationInfoEvent{
    data class ToggleFavorite(
        val id: Int
    ) : LocationInfoEvent
}

@HiltViewModel
class LocationInfoViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val repository: LocationRepository
) : BaseViewModel<Location, LocationInfoEvent>(
    loadingTime = 2.seconds
) {
    private val destination = savedStateHandle.toRoute<LocationInfoDestination>()

    init {
        triggerLoading()
    }

    override fun handleEvent(event: LocationInfoEvent) {
        when (event) {
            is LocationInfoEvent.ToggleFavorite -> toggleFavorite(event.id)
        }
    }

    private fun toggleFavorite(id: Int){
        viewModelScope.launch {
            repository.toggleLocationFavorite(id)
        }
    }

    override fun loadData(): Flow<Location> =
        repository.getLocation(destination.id)
}