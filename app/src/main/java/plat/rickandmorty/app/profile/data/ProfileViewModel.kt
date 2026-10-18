package plat.rickandmorty.app.profile.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import plat.rickandmorty.app.core.Dependencies
import plat.rickandmorty.app.core.data.PreferencesDataStore

class ProfileViewModel(app: Application) : AndroidViewModel(app) {
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
            Dependencies.database.characterDAO().deleteCharacters()
            Dependencies.database.locationDAO().deleteLocations()

            dataStore.removeUsername()
            onDone()
        }
    }
}