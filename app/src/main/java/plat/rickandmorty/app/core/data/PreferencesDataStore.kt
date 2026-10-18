package plat.rickandmorty.app.core.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import plat.rickandmorty.app.dataStore

class PreferencesDataStore(private val context: Context) {
    private val usernameKey = stringPreferencesKey("username")

    suspend fun setUsername(username: String) {
        context.dataStore.edit { it[usernameKey] = username }
    }

    suspend fun getUsername(): String {
        val preferences = context.dataStore.data.first()
        return preferences[usernameKey] ?: ""
    }

    suspend fun removeUsername() {
        context.dataStore.edit {it.remove(usernameKey)}
    }
}