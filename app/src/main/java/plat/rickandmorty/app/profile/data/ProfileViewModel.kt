package plat.rickandmorty.app.profile.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import plat.rickandmorty.app.character.data.CharacterRepository
import plat.rickandmorty.app.core.Dependencies
import plat.rickandmorty.app.core.data.PreferencesDataStore
import plat.rickandmorty.app.location.data.LocationRepository
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    app: Application,
    private val characterRepository: CharacterRepository,
    private val locationRepository: LocationRepository
) : AndroidViewModel(app) {
    private val dataStore = PreferencesDataStore(app.applicationContext)
    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    name = dataStore.getUsername()
                )
            }
        }
    }

    fun logout(onDone: () -> Unit) {
        viewModelScope.launch {
            characterRepository.deleteCharacters()
            locationRepository.deleteLocations()

            dataStore.removeUsername()
            onDone()
        }
    }
}