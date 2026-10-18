package plat.rickandmorty.app.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import plat.rickandmorty.app.character.data.CharacterDAO
import plat.rickandmorty.app.character.data.CharacterEntity
import plat.rickandmorty.app.location.data.LocationDAO
import plat.rickandmorty.app.location.data.LocationEntity

@Database(
    entities = [
        CharacterEntity::class,
        LocationEntity::class
    ],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun characterDAO(): CharacterDAO
    abstract fun locationDAO(): LocationDAO
}