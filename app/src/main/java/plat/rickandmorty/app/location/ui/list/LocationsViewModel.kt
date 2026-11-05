package plat.rickandmorty.app.location.ui.list

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import plat.rickandmorty.app.core.data.AppState
import plat.rickandmorty.app.core.data.BaseViewModel
import plat.rickandmorty.app.location.data.LocationRepository
import plat.rickandmorty.app.location.data.domain.Location
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

data class LocationsFilters(
    val mode: LocationsFilterMode = LocationsFilterMode.TODAS
)

enum class LocationsFilterMode{
    TODAS, FAVORITAS, NO_FAVORITAS
}

sealed interface LocationsEvent{
    data class ChangeFilter(
        val filter: LocationsFilterMode
    ) : LocationsEvent
    data class ToggleFavorite(
        val id: Int
    ) : LocationsEvent
}

@HiltViewModel
class LocationsViewModel @Inject constructor (
    private val locationRepository: LocationRepository
) : BaseViewModel<List<Location>, LocationsEvent>(
    loadingTime = 4.seconds
) {
    private val _filter = MutableStateFlow(LocationsFilters())
    val filter = _filter.asStateFlow()

    val filteredState: StateFlow<AppState<List<Location>>> =
        combine(state, _filter) { appState, filter ->
            appState.copy(
                data = appState.data?.filter { location ->
                    when (filter.mode) {
                        LocationsFilterMode.TODAS -> true
                        LocationsFilterMode.FAVORITAS -> location.favorite
                        LocationsFilterMode.NO_FAVORITAS -> !location.favorite
                    }
                }
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AppState()
        )

    init {
        triggerLoading()
    }

    override fun handleEvent(event: LocationsEvent) {
        when (event) {
            is LocationsEvent.ChangeFilter -> changeFilters(event.filter)
            is LocationsEvent.ToggleFavorite -> toggleFavorite(event.id)
        }
    }

    private fun changeFilters(mode: LocationsFilterMode) {
        _filter.update { it.copy(mode = mode) }
    }

    private fun toggleFavorite(id: Int) {
        viewModelScope.launch {
            locationRepository.toggleLocationFavorite(id)
        }
    }

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