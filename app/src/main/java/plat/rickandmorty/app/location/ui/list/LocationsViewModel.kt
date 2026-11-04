package plat.rickandmorty.app.location.ui.list

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import plat.rickandmorty.app.core.data.BaseViewModel
import plat.rickandmorty.app.location.data.LocationRepository
import plat.rickandmorty.app.location.data.domain.Location
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class LocationsViewModel @Inject constructor (
    private val locationRepository: LocationRepository
) : BaseViewModel<List<Location>, Nothing>(
    loadingTime = 4.seconds
) {
    init {
        triggerLoading()
    }

    override fun handleEvent(event: Nothing) {}

    override fun loadData(): Flow<List<Location>> {
        val locations = locationRepository.getLocations()

        viewModelScope.launch {
            if (locations.first().isEmpty()) {
                triggerError()
            }
        }

        return locations
    }
}